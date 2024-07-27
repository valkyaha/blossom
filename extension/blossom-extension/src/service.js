const sendCardData = async (channelId, data) => {
    try {

        let twitchToken;
        window.Twitch.ext.onAuthorized(function (auth) {
            // console.log('The JWT that will be passed to the EBS is', auth.token);
            console.log('The channel ID is', auth.channelId);
            console.log('The opaque user ID is', auth.userId);
            channelId = auth.channelId;
            twitchToken = auth.token;
        });

        const response = await fetch(`http://localhost:8080/cards/${channelId}`, {
            method: 'PUT', headers: {
                'Content-Type': 'application/json',
                'Authorization': `${twitchToken}`
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

export const handleSubmit = async (data) => {
    console.log("From data: ", data);
    const channelId = 'darkittyvt'
    await sendCardData(channelId, data);
};


export const getAbility = async (channelId) => {

    window.Twitch.ext.onAuthorized(function (auth) {
        // console.log('The JWT that will be passed to the EBS is', auth.token);
        console.log('The channel ID is', auth.channelId);
        console.log('The opaque user ID is', auth.userId);
        channelId = auth.channelId;
    });

    try {
        const response = await fetch(`http://localhost:8080/cards/${channelId}/abilities`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        });

        const text = await response.text();
        console.log('Response status:', response.status);
        console.log('Response text:', text);

        try {
            const result = JSON.parse(text);
            console.log('Parsed result:', result);
            return result;
        } catch (jsonParseError) {
            throw new Error(`Failed to parse JSON: ${jsonParseError.message}`);
        }
    } catch (err) {
        console.log('Error getting the ability due to: ', err);
        throw err;
    }
}


export const getType = async (channelId) => {

    window.Twitch.ext.onAuthorized(function (auth) {
        // console.log('The JWT that will be passed to the EBS is', auth.token);
        console.log('The channel ID is', auth.channelId);
        console.log('The opaque user ID is', auth.userId);
        channelId = auth.channelId;
    });

    try {
        const response = await fetch(`http://localhost:8080/cards/${channelId}/type`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
        });

        const text = await response.text();
        console.log('Response status:', response.status);
        console.log('Response text:', text);

        try {
            const result = JSON.parse(text);
            console.log('Parsed result:', result);
            return result;
        } catch (jsonParseError) {
            throw new Error(`Failed to parse JSON: ${jsonParseError.message}`);
        }
    } catch (err) {
        console.log('Error getting the ability due to: ', err);
        throw err;
    }
}