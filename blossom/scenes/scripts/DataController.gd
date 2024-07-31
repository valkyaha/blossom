extends Node

var database: SQLite
var configuration = ConfigFile.new()

var card_image = null
var card_name = ""
var ability_name = ""
var ability_description = ""
var card_damage = ""
var card_hp = ""
var card_type = ""

func database_connection():
	database = SQLite.new()
	database.path = "res://data/data.db"
	if not database.open_db():
		printerr("Failed to open database.")
	return database

func _ready():
	var err = configuration.load("user://settings.cfg")
	if err != OK:
		configuration.set_value("General", "resource_image_folder", "")
		configuration.save("user://settings.cfg")
		return
	database_connection()

	pass

func insert_card(data):
	data.erase("id")
	if not database.insert_row("cards", data):
		printerr("Failed to insert row into database.")
	pass

func update_card(data, card_id):
	card_name = data["name"]
	data.erase("id")
	if not database.update_rows("cards", "id = '" + str(card_id) + "'", data):
		printerr("Failed to update row in database.")
	pass

func select_card(card_id):
	var cards = database.select_rows("cards", "id = " + str(card_id), ["*"])
	if cards.size() == 0:
		printerr("No cards found with the given ID.")
		return
	
	var card = cards[0]
	card_name = card["name"]
	
	var card_type_result = database.select_rows("type", "id = " + str(card["type"]), ["*"])
	if card_type_result.size() > 0:
		card_type = card_type_result[0]["type"]
	
	card_damage = str(card["damage"])
	card_hp = str(card["health"])

	var ability_result = database.select_rows("ability", "id = " + str(card["ability"]), ["*"])
	if ability_result.size() > 0:
		ability_name = ability_result[0]["ability_name"]
		ability_description = ability_result[0]["effect"]

	if card["image"] != null:
		var image = Image.new()
		image.load_png_from_buffer(card["image"])
		var texture = ImageTexture.create_from_image(image)
		card_image = texture
	else:
		card_image = null
	
	return {
		"id": card_id,
		"image": card_image,
		"name": card_name,
		"ability_name": ability_name,
		"ability_description": ability_description,
		"damage": card_damage,
		"hp": card_hp,
		"type": card_type
	}

func delete_card(card_id):
	if not database.delete_rows("cards", "id = " + str(card_id)):
		printerr("Failed to delete row from database.")
	pass

func update_image(card_id, image):
	var pba = image.get_image().save_png_to_buffer()
	if not database.update_rows("cards", "id = " + str(card_id), {"image": pba}):
		printerr("Failed to update row in database.")
	pass

func set_image_folder(dir):
	configuration.set_value("General", "resource_image_folder", dir)
	configuration.save("user://settings.cfg")
	pass

# Test functions
func test_insert_card():
	var data = {
		"name": "Test Card",
		"ability": "Test Ability",
		"type": 1,
		"damage": 10,
		"health": 5,
		"image": null
	}
	insert_card(data)

func test_update_card():
	var data = {
		"id": 1,
		"name": "Updated Card",
		"ability": "Updated Ability",
		"type": 2,
		"damage": 20,
		"health": 10,
		"image": null
	}
	update_card(data, 1)
