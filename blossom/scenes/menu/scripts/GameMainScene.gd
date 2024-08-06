extends Control

func _ready():
	pass

func _on_settings_button_button_down():
	var sceneToLoad = preload("res://scenes/menu/Settings.tscn").instantiate()
	get_tree().root.add_child(sceneToLoad)
	pass

func _on_exit_button_button_down():
	get_tree().quit(0)
	pass

func _on_start_button_button_down():
	get_tree().change_scene_to_packed(load("res://scenes/game/levels/LevelOneAlpha.tscn"))
	pass
