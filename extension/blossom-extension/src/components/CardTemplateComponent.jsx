import React, { useState } from 'react';
import "./CardTemplateComponent.css";
import cardTemplate from '../static/CardTemplate.png';

export const CardTemplateComponent = ({ initialData, onSubmit }) => {
    const [data, setData] = useState(initialData);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setData({
            ...data,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit(data);
    };

    return (
        <form id="cardBody" onSubmit={handleSubmit}>
            <div id="cardImageContainer">
                <img id="cardImage" src={data.image} alt="Card" />
            </div>
            <img id="templateImage" alt="template" src={cardTemplate} />
            <div id="cardHeader">
                <input
                    type="text"
                    name="dmg"
                    value={data.dmg}
                    onChange={handleChange}
                    className="cardInput"
                    placeholder="DMG"
                />
                <input
                    type="text"
                    name="hp"
                    value={data.hp}
                    onChange={handleChange}
                    className="cardInput"
                    placeholder="HP"
                />
            </div>

            <input
                type="text"
                name="name"
                value={data.name}
                onChange={handleChange}
                id="name"
                className="cardInput"
                placeholder="Name"
            />
            <div id="cardFooter">
                <input
                    id="abilityName"
                    type="text"
                    name="abilityName"
                    value={data.abilityName}
                    onChange={handleChange}
                    className="cardInput"
                    placeholder="Ability Name"
                />
                <textarea
                    id="abilityDescription"
                    name="abilityDescription"
                    value={data.abilityDescription}
                    onChange={handleChange}
                    className="cardInput"
                    placeholder="Ability Description"
                    maxLength={108}
                    cols={50}
                    rows={4}
                />
                <div id="tpEncapsulation">
                    <input
                        type="text"
                        id="tp"
                        name="tp"
                        value={data.tp}
                        onChange={handleChange}
                        className="cardInput"
                        placeholder="TP"
                    />
                </div>
                {/*<button type="submit" className="submitButton">Save</button>*/}
            </div>
        </form>
    );
}
