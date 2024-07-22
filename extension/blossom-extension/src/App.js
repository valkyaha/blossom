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

const sendCardData = async (channelId, data) => {
    window.Twitch.ext.onAuthorized(function(auth) {
        // console.log('The JWT that will be passed to the EBS is', auth.token);
        console.log('The channel ID is', auth.channelId);
        console.log('The opaque user ID is', auth.userId);
    });

    try {
        const response = await fetch(`http://localhost:8080/cards/${channelId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const result = await response.json();
        console.log('Response from server: ', result);
    } catch (err) {
        console.error('Error sendig card data: ', err);
    }
}

const handleSubmit = (data) => {
    console.log("From data: ", data);
    const channelId = 'darkittyvt'
    sendCardData(channelId, data);
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
