import './App.css';
import { CardTemplateComponent } from "./components/CardTemplateComponent";
import { getAbility, getType, handleSubmit } from './service';
import { useState, useEffect } from 'react';

const cardData = {
    dmg: '10',
    hp: '20',
    image: 'https://www.ikea.com/es/es/images/products/blahaj-peluche-tiburon__0710175_pe727378_s5.jpg?f=xl',
    name: 'Shikanoko Noko',
    abilityName: '',
    abilityDescription: '',
    tp: '5'
};

function App() {
    const [abilities, setAbilities] = useState([]);
    const [types, setTypes] = useState([]);

    useEffect(() => {
        getAbility("dsadsa").then(response => {
            if (response.status === "OK") {
                setAbilities(response.objectResponse);
            }
        });

        getType("dasad").then(response => {
            if (response.status === "OK") {
                setTypes(response.objectResponse);
            }
        });
    }, []);

    return (
        <div className="App">
            <header className="App-header">
                <CardTemplateComponent initialData={cardData} abilities={abilities} types={types} onSubmit={handleSubmit} />
            </header>
        </div>
    );
}

export default App;
