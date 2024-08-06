extends Control
const MAX_HEALTH = 1000
var health = MAX_HEALTH

@onready var label
@onready var boss_bar = $BossBar

func _ready() -> void:
	boss_bar.max_value = MAX_HEALTH
	set_health_bar()

func set_health_bar() -> void:
	boss_bar.value = health


func card_damage_deal(card_damage):
	health -= card_damage
	if health < 0:
		health = MAX_HEALTH
	set_health_bar()
