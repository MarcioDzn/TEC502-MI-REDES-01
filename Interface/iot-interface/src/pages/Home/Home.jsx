import { SensorCard } from "../../components/SensorCard/SensorCard";
import { useQuery } from '@tanstack/react-query';
import { addDevice, getDevices, setDeviceOffline, setDeviceOnline } from "../../services/deviceService";
import {useEffect, useState} from "react"
import { DeviceList, HeaderContainer, HomeContainer } from "./HomeStyled";
import { ControlPanel } from "../../components/ControlPanel/ControlPanel";
import { useMutation } from '@tanstack/react-query';

export function Home() {
    const [selectedSensorIndex, setSelectedSensorIndex] = useState(-1);

    const {data: devices, isFetching, isError, refetch} = useQuery({
        queryKey: ["devices"],
        queryFn: getDevices
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

    return (
        <HomeContainer>
            <HeaderContainer>
                <h1>Dispositivos</h1>

                <form onSubmit={(event) => {
                        event.preventDefault();
                        handleSubmit(document.getElementById("inputAddDevice").value)
                    }}>
                    <input type="text" id="inputAddDevice"/>
                    <button type="submit">Adicionar</button>
                </form>
            </HeaderContainer>

            <div>
                <DeviceList>
                    {
                        <>
                            {devices?.data?.map(item => (
                                <li key={item.id}><SensorCard selected={selectedSensorIndex == item.id} id={item.id} name={item.name} time={item.time} value={item.data} status={item.status} onClick={handleSelectSensor}/></li> 
                            ))}
                        </>
                    }

                </DeviceList>   
                
                
                <ControlPanel device={selectedSensorIndex > -1 ? devices.data[selectedSensorIndex] : null} 
                    handleOffline = {() => {devices.data[selectedSensorIndex].data == "offline" ? 
                        setOnlineMutation(selectedSensorIndex) : 
                        setOfflineMutation(selectedSensorIndex)}}
                >
                
                </ControlPanel>
            </div>
            
        </HomeContainer>

    )
}