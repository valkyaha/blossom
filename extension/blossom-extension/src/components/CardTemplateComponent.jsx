import React, { useState, useEffect, useCallback } from 'react';
import "./CardTemplateComponent.css";
import cardTemplate from '../static/CardTemplate.png';

export const CardTemplateComponent = ({ initialData, abilities, types, onSubmit }) => {
    const [data, setData] = useState(initialData);
    const maxPoints = 30;

    useEffect(() => {
        const selectedAbility = abilities.find(ability => ability.ability_name === data.abilityName);
        if (selectedAbility) {
            setData(prevData => ({
                ...prevData,
                abilityDescription: selectedAbility.effect
            }));
        }
    }, [data.abilityName, abilities]);

    const handleChange = useCallback((e) => {
        const { name, value } = e.target;
        setData(prevData => ({
            ...prevData,
            [name]: value
        }));
    }, []);

    const handleDmgChange = useCallback((e) => {
        const value = parseInt(e.target.value, 10);
        if (!isNaN(value) && value >= 0 && value + parseInt(data.hp, 10) <= maxPoints) {
            setData(prevData => ({
                ...prevData,
                dmg: value
            }));
        }
    }, [data.hp]);

    const handleHpChange = useCallback((e) => {
        const value = parseInt(e.target.value, 10);
        if (!isNaN(value) && value >= 0 && value + parseInt(data.dmg, 10) <= maxPoints) {
            setData(prevData => ({
                ...prevData,
                hp: value
            }));
        }
    }, [data.dmg]);

    const handleAbilityChange = useCallback((e) => {
        const { value } = e.target;
        const selectedAbility = abilities.find(ability => ability.ability_name === value);
        setData(prevData => ({
            ...prevData,
            abilityName: value,
            abilityDescription: selectedAbility ? selectedAbility.effect : ''
        }));
    }, [abilities]);

    const handleSubmit = useCallback((e) => {
        e.preventDefault();
        onSubmit(data);
    }, [data, onSubmit]);

    return (
        <form id="cardBody" onSubmit={handleSubmit}>
            <div id="cardImageContainer">
                <img id="cardImage" src={data.image} alt="Card" />
            </div>
            <img id="templateImage" alt="Template" src={cardTemplate} />
            <div id="cardHeader">
                <input
                    type="number"
                    name="dmg"
                    value={data.dmg || ''}
                    onChange={handleDmgChange}
                    className="cardInputDMGHP"
                    placeholder="DMG"
                    min="0"
                />
                <input
                    type="number"
                    name="hp"
                    value={data.hp || ''}
                    onChange={handleHpChange}
                    className="cardInputDMGHP"
                    placeholder="HP"
                    min="0"
                />
            </div>

            <input
                type="text"
                name="name"
                value={data.name || ''}
                onChange={handleChange}
                id="name"
                className="cardInput"
                placeholder="Name"
            />
            <div id="cardFooter">
                <select
                    id="cardAbilitySelector"
                    name="abilityName"
                    value={data.abilityName || ''}
                    onChange={handleAbilityChange}
                    className="cardInput"
                >
                    {abilities.map((ability, index) => (
                        <option key={index} value={ability.ability_name}>
                            {ability.ability_name}
                        </option>
                    ))}
                </select>
                <textarea
                    id="abilityDescription"
                    name="abilityDescription"
                    value={data.abilityDescription || ''}
                    onChange={handleChange}
                    className="cardInput"
                    placeholder="Ability Description"
                    maxLength={108}
                    cols={50}
                    rows={4}
                    readOnly
                />

                <select
                    name="tp"
                    value={data.tp || ''}
                    onChange={handleChange}
                    className="cardInput"
                >
                    {types.map((type, index) => (
                        <option key={index} value={type.type}>
                            {type.type}
                        </option>
                    ))}
                </select>
                <button type="submit" className="submitButton">Save</button>
            </div>
        </form>
    );
}
