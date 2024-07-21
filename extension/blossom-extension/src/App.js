import logo from './logo.svg';
import './App.css';
import {CardTemplateComponent} from "./components/CardTemplateComponent";

const cardData = {
    dmg: '10',
    hp: '20',
    image: 'path_to_image.png',
    name: 'Hero Name',
    abilityName: 'Special Ability',
    abilityDescription: 'This is a description of the special ability.',
    tp: '5'
};

function App() {
    return (
        <div className="App">
            <header className="App-header">
                <a
                    className="App-link"
                    href="https://reactjs.org"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    PITO CORTO IS ALIVE adsadasdas
                </a>
                <CardTemplateComponent data={cardData}/>
            </header>
        </div>
    );
}

export default App;
