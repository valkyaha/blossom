extends Control

var database: SQLite
@onready var card_name = $CardName
@onready var card_damage = $CardDamage
@onready var card_health = $CardHealth
@onready var card_ability_name = $CardAbilityName
@onready var card_ability_description = $CardAbilityDescription
@onready var card_type = $CardType


# Called when the node enters the scene tree for the first time.
func _ready():
	database = SQLite.new()
	database.path="res://data/data.db"
	database.open_db()
	pass # Replace with function body.


# Called every frame. 'delta' is the elapsed time since the previous frame.
func _process(delta):
	pass


func _on_insert_button_down():
	var data = {
		"type" : int($CardType.text),
		"name" : $CardName.text,
		"damage" : int($CardDamage.text),
		"health" : int($CardHealth.text),
		"ability" : int($CardAbilityName.text)
	}
	
	database.insert_row("cards",data)
	print(data)
	pass # Replace with function body.


func _on_update_button_down():
	var data = {
		"type" : int($CardType.text),
		"name" : $CardName.text,
		"damage" : int($CardDamage.text),
		"health" : int($CardHealth.text),
		"ability" : int($CardAbilityName.text)
	}

	print(data)
	print($CardName.text)
	database.update_rows("cards","name = '" + $CardName.text + "'",data)
	
	pass # Replace with function body.


func _on_select_button_down():
	var cards = database.select_rows("cards","id = 1",["*"])
	for i in cards: 
		card_name.text = i["name"]
		card_type.text = str(i["type"])
		card_damage.text = str(i["damage"])
		card_health.text = str(i["health"])
		var ability = database.select_rows("ability","id = 1",["*"])
		card_ability_name.text = ability[0]["ability_name"]
		card_ability_description.text = ability[0]["effect"]
		print(i)
	pass # Replace with function body.


func _on_delete_button_down():
	pass # Replace with function body.


func _on_query_button_down():
	
	var image := preload("res://resources/CardTemplate/Cards_color1/Civilian_card_back/details/Background.png")
	var pba = image.get_image().save_png_to_buffer()
	database.update_rows("cards","id = 1",{"image": pba})
	pass # Replace with function body.
