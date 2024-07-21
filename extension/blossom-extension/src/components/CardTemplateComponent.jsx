import React from 'react';
import "./CardTemplateComponent.css";

export const CardTemplateComponent = ({ data }) => {
    return (
        <div id="cardBody">
            <div id="cardHeader">
                <div id="dmg">{data.dmg}</div>
                <div id="hp">{data.hp}</div>
            </div>
            <div id="cardImageContainer">
                <img id="cardImage" src={data.image} alt="Card Image" />
            </div>
            <div id="cardFooter">
                <div id="name">{data.name}</div>
                <div id="abilityName">{data.abilityName}</div>
                <div id="abilityDescription">{data.abilityDescription}</div>
                <div id="tp">{data.tp}</div>
            </div>
        </div>
    );
}
