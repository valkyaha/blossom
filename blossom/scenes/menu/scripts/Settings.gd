extends Control
var configuration = ConfigFile.new()

func _ready():
	var err = configuration.load("user://settings.cfg")
	if err != OK:
		configuration.set_value("General", "resource_image_folder", "")
		configuration.save("user://settings.cfg")
		return
	pass

func _process(delta):
	pass


func _on_settings_button_button_down():
	get_parent().remove_child(self)


func _on_exit_button_button_down():
	get_parent().remove_child(self)


func _on_option_button_item_selected(index):

	match index:
		0:
			get_window().set_size(Vector2(2560, 1440))
		1:
			get_window().set_size(Vector2(1920, 1080))
		2:
			get_window().set_size(Vector2(1366, 768))
		3:
			get_window().set_size(Vector2(1280, 720))
		4:
			get_window().set_size(Vector2(1920, 1200))
		5:
			get_window().set_size(Vector2(1680, 1050))
		6:
			get_window().set_size(Vector2(1440, 900))
		7:
			get_window().set_size(Vector2(1280, 800))
		8:
			get_window().set_size(Vector2(1024, 768))
		9:
			get_window().set_size(Vector2(800, 600))
		10:
			get_window().set_size(Vector2(640, 480))
	center_window()
	pass

func center_window():
	var screen_center = DisplayServer.screen_get_position() + DisplayServer.screen_get_size() / 2
	var window_size = get_window().get_size_with_decorations()
	get_window().set_position(screen_center - window_size /2)


func _on_save_button_pressed():
	pass
