import './App.css';
import {CardTemplateComponent} from "./components/CardTemplateComponent";

const cardData = {
    dmg: '10',
    hp: '20',
    image: 'https://www.ikea.com/es/es/images/products/blahaj-peluche-tiburon__0710175_pe727378_s5.jpg?f=xl',
    name: 'Shikanoko Noko',
    abilityName: 'Shikanoko',
    abilityDescription: 'Shikanoko noko koshi tan tan shikanoko noko koshi tan tan shikanoko noko koshi tan tan shikanoko noko koshi tan tan',
    tp: '5'
};

const handleSubmit = (data) => {
    console.log('Form data:', data);
};

function App() {
    return (
        <div className="App">
            <header className="App-header">
                <CardTemplateComponent initialData={cardData} onSubmit={handleSubmit}/>
            </header>
        </div>
    );
}

export default App;
