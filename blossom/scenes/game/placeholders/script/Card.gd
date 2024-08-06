extends Control

var card_id = 0

func _ready():
	pass

func _process(delta):
	pass

func data(id, image, card_name, ability_name, ability_description, card_damage, card_hp, card_type):
	$".".texture = image
	$PanelContainer/TemplateCard/CardName.text = card_name
	$PanelContainer/TemplateCard/CardAbilityName.text = ability_name
	$PanelContainer/TemplateCard/CardAbilityDesc.text = ability_description
	$PanelContainer/TemplateCard/HFlowContainer/DMGLabel.text = card_damage
	$PanelContainer/TemplateCard/HFlowContainer/HPLabel.text = card_hp
	$PanelContainer/TemplateCard/TypeLabel.text = card_type
	card_id = id


func get_input_data():
	var data = {
		"id" : card_id,
		"type": int($CardType.text),
		"name": $CardName.text,
		"damage": int($CardDamage.text),
		"health": int($CardHP.text),
	}
	return data

func export_data():
	var image_data = null
	var pba = null
	if $".".texture != null:
		pba = $".".texture.get_image().save_png_to_buffer()

	var data = {
		"id" : card_id,
		"image": pba,
		"name": $CardName.text,
		"damage": int($CardDamage.text),
		"health": int($CardHP.text),
		"type": int($CardType.text)
	}
	return data
