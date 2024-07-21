import logo from './logo.svg';
import './App.css';
import {CardTemplateComponent} from "./components/CardTemplateComponent";

const cardData = {
    dmg: '10',
    hp: '20',
    image: 'https://preview.redd.it/deer-godskin-shikanoko-the-scarlet-brainrot-v0-dkvl85w24pad1.jpeg?width=1920&format=pjpg&auto=webp&s=913c408a1333fff6aa3f00f91792a0cf056d0636',
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
