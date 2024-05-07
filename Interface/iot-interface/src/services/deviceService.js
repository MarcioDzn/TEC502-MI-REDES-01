import axios from "axios";

const brokerIp = localStorage.getItem("broker_ip");
const baseURL = `http://${brokerIp ? brokerIp : "localhost"}:8080`

export async function getDevices(setServerOnline) {
    try {
        const controller = new AbortController();
        const signal = controller.signal;
    
        // se demorar 8 segundos dá erro
        const timeoutId = setTimeout(() => {
            controller.abort();
        }, 8000);
    
        let response = await fetch(`${baseURL}/api/sensor`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            signal 
        });
    
        clearTimeout(timeoutId); 
    
        response = await response.json();
        setServerOnline(true);
        return response;
    
    // se der erro returna uma lista vazia e identifica o broker como offline
    } catch (error) {
        setServerOnline(false);
        return [];
    }
}

export function setDeviceOffline(id) {
    const body = {
        command: "turn_off"
    }
    axios.patch(`${baseURL}/api/sensor/${id}`, body);
}

export function setDeviceOnline(id) {
    const body = {
        command: "turn_on"
    }
    axios.patch(`${baseURL}/api/sensor/${id}`, body);
}

export function addDevice(ip, ip2) {
    const port = 3002
    const body = {
        ip: ip,
        port: port
    }
    axios.post(`${baseURL}/api/sensor`, body);
}