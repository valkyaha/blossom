extends Control

var database: SQLite
var amountOfUsers = 5;

@onready var random_numer = $RandomNumer
@onready var control_card_id = $ControlCardId

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
	pass


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
	
	pass


func _on_select_button_down():
	var cards = database.select_rows("cards","id =" + $ControlCardId.text,["*"])
	
	if (cards.size() == 0):
		$CardName.text = ""
		$CardType.text = ""
		$CardDamage.text = ""
		$CardHealth.text = ""
		$CardAbilityName.text = ""
		$CardAbilityDescription.text = ""
		$CardImage.texture = null
	
	for i in cards: 
		$CardName.text = i["name"]
		var type = database.select_rows("type","id =" + str(i["type"]),["*"])
		$CardType.text = type[0]["type"]
		$CardDamage.text = str(i["damage"])
		$CardHealth.text = str(i["health"])
		
		var ability = database.select_rows("ability","id ="+ str(i["ability"]),["*"])
		$CardAbilityName.text = ability[0]["ability_name"]
		$CardAbilityDescription.text = ability[0]["effect"]
		
		if(i["image"] != null):		
			var image = Image.new()
			image.load_png_from_buffer(i["image"])
			var texture = ImageTexture.create_from_image(image)
			$CardImage.texture = texture
		else :
			$CardImage.texture = null
	pass


func _on_delete_button_down():
	pass


func _on_query_button_down():
	
	var image := preload("res://resources/CardTemplate/Cards_color1/Civilian_card_back/details/Background.png")
	var pba = image.get_image().save_png_to_buffer()
	database.update_rows("cards","id = 1",{"image": pba})

	pass


func _on_roulette_button_down():
	random_numer.text = str(randi() % amountOfUsers)
	
	pass
