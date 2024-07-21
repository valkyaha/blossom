import React, { useState } from 'react';
import "./CardTemplateComponent.css";

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
            <div id="cardImageContainer">
                <img id="cardImage" src={data.image} alt="Card Image" />
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
                    type="text"
                    name="abilityName"
                    value={data.abilityName}
                    onChange={handleChange}
                    className="cardInput"
                    placeholder="Ability Name"
                />
                <input
                    type="text"
                    name="abilityDescription"
                    value={data.abilityDescription}
                    onChange={handleChange}
                    className="cardInput"
                    placeholder="Ability Description"
                />
                <input
                    type="text"
                    name="tp"
                    value={data.tp}
                    onChange={handleChange}
                    className="cardInput"
                    placeholder="TP"
                />
                {/*<button type="submit" className="submitButton">Save</button>*/}
            </div>
        </form>
    );
}
