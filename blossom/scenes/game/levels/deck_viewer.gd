extends Control

var card_id = 0
var cards: Array
var card_image
var card_name
var card_damage
var card_hp
var ability_name
var ability_description
var card_type
var database : SQLite
const DataController = preload("res://scenes/scripts/DataController.gd")
@onready var grid_container = $MarginContainer/GridContainer
const CARD = preload("res://scenes/game/placeholders/Card.tscn")

func _ready():
	database = DataController.new().database_connection()
	_get_cards_data()
	for i in cards.size():
		_create_deck_on_card_size(i)


func _get_cards_data():
	cards = database.select_rows("cards", "", ["*"])

func _process(delta):
	pass


func _create_deck_on_card_size(i):
	print(i)
	var card = cards[i]
	card_id = i
	card_name = card["name"]
	_get_card_type(card)
	_get_card_ability(card)
	var new_card = CARD.instantiate()
	new_card.data(card_id, card_image, card_name, ability_name, ability_description, card_damage, card_hp, card_type)
	grid_container.add_child(new_card)


func _get_card_type(card):
	var card_type_result = database.select_rows("type", "id = " + str(card["type"]), ["*"])
	
	if card_type_result.size() > 0:
		card_type = card_type_result[0]["type"]
		card_damage = str(card["damage"])
		card_hp = str(card["health"])


func _get_card_ability(card):
	var ability_result = database.select_rows("ability", "id = " + str(card["ability"]), ["*"])
	if ability_result.size() > 0:
		ability_name = ability_result[0]["ability_name"]
		ability_description = ability_result[0]["effect"]
