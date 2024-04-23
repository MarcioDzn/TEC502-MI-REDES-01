import axios from "axios";

const baseURL = "http://localhost:8080"

export function getDevices() {
    const response = axios.get(`${baseURL}/api/sensor`);

    return response;
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