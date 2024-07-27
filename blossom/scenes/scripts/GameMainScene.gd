extends Control

var websocket_url = "ws://localhost:8080/ws"
var _client = WebSocketPeer.new()

func _ready():
	_client.connect_to_url(websocket_url)
	
func _process(delta):
	_client.poll()
	var state = _client.get_ready_state()
	if state == WebSocketPeer.STATE_OPEN:
		while _client.get_available_packet_count():
			print("Packet: ", _client.get_packet())
	elif state == WebSocketPeer.STATE_CLOSING:
		pass
	elif state == WebSocketPeer.STATE_CLOSED:
		var code = _client.get_close_code()
		var reason = _client.get_close_reason()
		print("WebSocket closed with code: %d, reason %s. Clean: %s" % [code, reason, code != -1])
		set_process(false) # Stop processing.



func _on_settings_button_button_down():
	var sceneToLoad = preload("res://scenes/Settings.tscn").instantiate()
	get_tree().root.add_child(sceneToLoad)
	pass # Replace with function body.
