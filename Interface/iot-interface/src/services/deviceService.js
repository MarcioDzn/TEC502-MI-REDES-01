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

export function pauseDevice(id) {
    const body = {
        command: "pause"
    }
    axios.patch(`${baseURL}/api/sensor/${id}`, body);
}

export function unpauseDevice(id) {
    const body = {
        command: "unpause"
    }
    axios.patch(`${baseURL}/api/sensor/${id}`, body);
}