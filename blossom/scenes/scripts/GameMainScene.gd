extends Control

var database: SQLite
var websocket_url = "ws://localhost:8080/ws"
var _client = WebSocketPeer.new()

func _ready():
	database = SQLite.new()
	database.path = "res://data/data.db"
	if not database.open_db():
		printerr("Failed to open database.")
	_client.connect_to_url(websocket_url + "/670546954")

func _process(delta):
	_client.poll()
	var state = _client.get_ready_state()
	
	if state == WebSocketPeer.STATE_OPEN:
		while _client.get_available_packet_count() > 0:
			var packet = _client.get_packet().get_string_from_utf8()
			if not packet.is_empty():
				print("Received packet: ", packet)
				
				if packet.contains("GET_CARD"):
					var card = get_cards_from_database(1)
					print(card)
					_client.send_text(card)
					print("Sent response: ", card)
	
	elif state == WebSocketPeer.STATE_CLOSING:
		pass
	
	elif state == WebSocketPeer.STATE_CLOSED:
		var code = _client.get_close_code()
		var reason = _client.get_close_reason()
		print("WebSocket closed with code: %d, reason: %s. Clean: %s" % [code, reason, code != -1])
		set_process(false) # Stop processing.

func _on_settings_button_button_down():
	var sceneToLoad = preload("res://scenes/menu/Settings.tscn").instantiate()
	get_tree().root.add_child(sceneToLoad)
	pass # Replace with function body.

func _on_exit_button_button_down():
	get_tree().quit(0)
	pass

func get_cards_from_database(card_id_request):
	var data = {
		"dmg": "",
		"hp": "",
		"image": "",
		"name": "",
		"ability": {
			"id": "",
			"ability_name": "",
			"effect": "",
			"damage": "",
			"buff": "",
			},
			"type": {
				"id": "",
				"type": ""
				}
		}

	var cards = database.select_rows("cards", "id = " + str(card_id_request), ["*"])
	if cards.size() == 0:
		return null
	
	var card = cards[0]
	data["name"] = card["name"]
	data["dmg"] = str(card["damage"])
	data["hp"] = str(card["health"])
	
	var card_type_result = database.select_rows("type", "id = " + str(card["type"]), ["*"])
	if card_type_result.size() > 0:
		var card_type = card_type_result[0]
		data["type"]["id"] = str(card_type["id"])
		data["type"]["type"] = card_type["type"]
		
		var ability_result = database.select_rows("ability", "id = " + str(card["ability"]), ["*"])
		if ability_result.size() > 0:
			var ability = ability_result[0]
			data["ability"]["id"] = str(ability["id"])
			data["ability"]["ability_name"] = ability["ability_name"]
			data["ability"]["effect"] = ability["effect"]
			data["ability"]["damage"] = str(ability["damage"])
			data["ability"]["buff"] = str(ability["buff"])
			data["image"] = card["image"]
		
		return data

func encode_image_to_base64(image_data):
	if image_data != null:
		var image = Image.new()
		image.load_png_from_buffer(image_data)
		var byte_array = image.save_png_to_buffer()
		return byte_array
	return null


func _on_start_button_button_down():
	get_tree().change_scene_to_packed(load("res://scenes/game/LevelOneAlpha.tscn"))
	pass # Replace with function body.
