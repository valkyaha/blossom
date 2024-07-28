import './App.css';
import { useState, useEffect } from 'react';
import { CardTemplateComponent } from "./components/CardTemplateComponent";
import { getAbility, getType, handleSubmit, fetchCardData } from './services/service';

function App() {
    const [abilities, setAbilities] = useState([]);
    const [types, setTypes] = useState([]);
    const [cardData, setCardData] = useState(null); // Almacena los datos de la carta
    const [initialData, setInitialData] = useState({
        dmg: '0',
        hp: '0',
        image: '',
        name: '',
        abilityName: '',
        abilityDescription: '',
        tp: ''
    });

    useEffect(() => {
        // Fetch abilities and types
        getAbility().then(response => {
            if (response.status === "OK") {
                setAbilities(response.objectResponse);
            }
        });

        getType().then(response => {
            if (response.status === "OK") {
                setTypes(response.objectResponse);
            }
        });

        // Fetch card data
        fetchCardData().then(response => {
            if (response.status === "OK" && response.objectResponse) {
                setCardData(response.objectResponse);
                setInitialData({
                    ...response.objectResponse,
                    abilityDescription: response.objectResponse.abilityDescription || ''
                });
            }
        });

    }, []);

    return (
        <div className="App">
            <header className="App-header">
                <CardTemplateComponent
                    initialData={initialData}
                    abilities={abilities}
                    types={types}
                    onSubmit={handleSubmit}
                    cardData={cardData}
                />
            </header>
        </div>
    );
}

export default App;
