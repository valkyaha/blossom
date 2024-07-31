extends Control
const MAX_HEALTH = 5
var health = MAX_HEALTH

@onready var label
@onready var boss_bar = $BossBar

func _ready() -> void:
	boss_bar.max_value = MAX_HEALTH
	set_health_bar()

func set_health_bar() -> void:
	boss_bar.value = health

func _input(event: InputEvent) -> void:
	if event.is_action_pressed("ui_accept"):
		damage()

func damage() -> void:
	health -= 1
	if health < 0:
		health = MAX_HEALTH
	set_health_bar()
