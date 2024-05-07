import axios from "axios";

const brokerIp = localStorage.getItem("broker_ip");
const baseURL = `http://${brokerIp ? brokerIp : "localhost"}:8080`

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