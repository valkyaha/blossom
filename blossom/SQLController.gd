extends Control

var database: SQLite
var amountOfUsers = 5

@onready var card_placeholder = $CardPlaceholder
@onready var random_number = $RandomNumber
@onready var control_card_id = $ControlCardId
@onready var ability = $Ability
@onready var type = $Type

var card_image = null
var card_name = ""
var ability_name = ""
var ability_description = ""
var card_damage = ""
var card_hp = ""
var card_type = ""

func _ready():
	database = SQLite.new()
	database.path = "res://data/data.db"
	if not database.open_db():
		printerr("Failed to open database.")
	pass

func _on_insert_button_down():
	var data = card_placeholder.export_data()
	data["ability"] = ability.text
	data["type"] = type.text.to_int()
	data.erase("id")
	if not database.insert_row("cards", data):
		printerr("Failed to insert row into database.")
	pass

func _on_update_button_down():
	var data = card_placeholder.export_data()
	data["ability"] = ability.text
	data["type"] = type.text.to_int()
	card_name = data["name"]
	var str_card_id = str(data["id"])
	data.erase("id")
	if not database.update_rows("cards", "id = '" + str_card_id + "'", data):
		printerr("Failed to update row in database.")
	pass

func _on_select_button_down():
	var cards = database.select_rows("cards", "id = " + control_card_id.text, ["*"])
	if cards.size() == 0:
		card_placeholder.data(0, null, "", "", "", "", "", "")
		printerr("No cards found with the given ID.")
		return
	
	var card = cards[0]
	card_name = card["name"]
	var card_id = card["id"]
	
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
	
	card_placeholder.data(card_id, card_image, card_name, ability_name, ability_description, card_damage, card_hp, card_type)
	pass

func _on_delete_button_down():
	var card_id = control_card_id.text
	if not database.delete_rows("cards", "id = " + card_id):
		printerr("Failed to delete row from database.")
	pass

func _on_query_button_down():
	var image = preload("res://resources/CardTemplate/Cards_color1/Civilian_card_back/details/Background.png")
	var pba = image.get_image().save_png_to_buffer()
	if not database.update_rows("cards", "id = 1", {"image": pba}):
		printerr("Failed to update row in database.")
	pass

func _on_roulette_button_down():
	$RandomNumer.text = str(randi() % amountOfUsers)
	pass
