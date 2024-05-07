import { SensorCard } from "../../components/SensorCard/SensorCard";
import { useQuery } from '@tanstack/react-query';
import { addDevice, getDevices, setDeviceOffline, setDeviceOnline } from "../../services/deviceService";
import {useEffect, useState} from "react"
import { BrokerControlContainer, DeviceList, Form, HeaderContainer, HomeContainer } from "./HomeStyled";
import { ControlPanel } from "../../components/ControlPanel/ControlPanel";
import { useMutation } from '@tanstack/react-query';

export function Home() {
    const [selectedSensorIndex, setSelectedSensorIndex] = useState(-1);
    const [serverOnline, setServerOnline] = useState(false);

    const {data: devices, isFetching, isError, refetch} = useQuery({
        queryKey: ["devices"],
        queryFn: async () => {
            try {
                const brokerIp = localStorage.getItem("broker_ip");
                const baseURL = `http://${brokerIp ? brokerIp : "localhost"}:8080`;
            
                const controller = new AbortController();
                const signal = controller.signal;
            
                // Define o timeout para 8 segundos
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
            
            } catch (error) {
                setServerOnline(false);
                return [];
            }
        }
    })

    const {mutate: setOfflineMutation, isPending: isPendingOffline} = useMutation({
        mutationFn: setDeviceOffline
    })

    const {mutate: setOnlineMutation, isPending: isPendingOnline} = useMutation({
        mutationFn: setDeviceOnline
    })

    const {mutate: setAddDeviceMutation, isPending: isPendingAddDeviceMutation} = useMutation({
        mutationFn: addDevice
    })

    useEffect(() => {
        const id = setInterval(() => {
            refetch();
        }, 200); 
        
        // Limpando o timer quando o componente é desmontado
        return () => clearInterval(id);
    }, [refetch]);

    function handleSelectSensor(index) {
        if (index == selectedSensorIndex) {
            setSelectedSensorIndex(-1)
        } else {
            setSelectedSensorIndex(index);
        }
    }

    function handleSubmit(ip) {
        setAddDeviceMutation(ip, 3000)
    }

    function handleActiveBroker(ip) {
        localStorage.setItem("broker_ip", ip);
    }

    return (
        <HomeContainer>
            <BrokerControlContainer>
                <Form onSubmit={(event) => {
                        handleActiveBroker(document.getElementById("inputActiveBroker").value)
                    }}>
                    <input type="text" id="inputActiveBroker" placeholder="IP do Broker"/>
                    <button type="submit">Ativar Broker</button>
                </Form>
                <span>{!serverOnline ? "Broker desconectado!" : `Broker de IP ${localStorage.getItem("broker_ip")} conectado!`}</span>
            </BrokerControlContainer>

            <HeaderContainer>
                <h1>Dispositivos</h1>

                <Form onSubmit={(event) => {
                        event.preventDefault();
                        handleSubmit(document.getElementById("inputAddDevice").value)
                        document.getElementById("inputAddDevice").value = ""
                    }}>
                    <input type="text" id="inputAddDevice" placeholder="IP do dispositivo"/>
                    <button type="submit">Adicionar dispositivo</button>
                </Form>
            </HeaderContainer>

            <div>
                <DeviceList>
                    { !serverOnline ? <h1></h1> :
                        <>
                            {devices?.map(item => (
                                <li key={item.id}><SensorCard selected={selectedSensorIndex == item.id} id={item.id} name={item.name} time={item.time} value={item.data} status={item.status} onClick={handleSelectSensor}/></li> 
                            ))}
                        </>
                    }

                </DeviceList>   
                
                
                <ControlPanel device={selectedSensorIndex > -1 ? devices[selectedSensorIndex] : null} 
                    handleOffline = {() => {devices[selectedSensorIndex].data == "offline" ? 
                        setOnlineMutation(selectedSensorIndex) : 
                        setOfflineMutation(selectedSensorIndex)}}
                >
                
                </ControlPanel>
            </div>
            
        </HomeContainer>

    )
}