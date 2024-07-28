const baseUrl = 'http://localhost:8080';

const getAuthToken = () => {
    return new Promise((resolve) => {
        window.Twitch.ext.onAuthorized(function (auth) {
            resolve(auth.token);
        });
    });
};

const fetchWithAuth = async (url, options = {}) => {
    const token = await getAuthToken();
    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
        ...options.headers
    };

    try {
        const response = await fetch(url, {
            ...options,
            headers
        });
        return await response.json();
    } catch (err) {
        console.error(`Error with request to ${url}:`, err);
        throw err;
    }
};

export const fetchCardData = async () => {
    return await fetchWithAuth(`${baseUrl}/cards`, { method: 'GET' });
};

export const handleSubmit = async (data) => {
    await fetchWithAuth(`${baseUrl}/cards`, {
        method: 'PUT',
        body: JSON.stringify(data)
    });
};

export const getAbility = async () => {
    return await fetchWithAuth(`${baseUrl}/cards/abilities`, { method: 'GET' });
};

export const getType = async () => {
    return await fetchWithAuth(`${baseUrl}/cards/type`, { method: 'GET' });
};
