import { SensorCard } from "../../components/SensorCard/SensorCard";
import { useQuery } from '@tanstack/react-query';
import { getDevices, pauseDevice, setDeviceOffline, setDeviceOnline, unpauseDevice } from "../../services/deviceService";
import {useEffect, useState} from "react"
import { DeviceList, HomeContainer } from "./HomeStyled";
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

    const {mutate: setPauseMutation, isPending: isPendingPause} = useMutation({
        mutationFn: pauseDevice
    })

    const {mutate: setUnpauseMutation, isPending: isPendingUnpause} = useMutation({
        mutationFn: unpauseDevice
    })

    useEffect(() => {
        const id = setInterval(() => {
            refetch();
        }, 200); 
        
        // Limpando o timer quando o componente é desmontado
        return () => clearInterval(id);
    }, [refetch]);

    function handleSelectSensor(index) {
        setSelectedSensorIndex(index);
    }

    return (
        <HomeContainer>
            <h1>Dispositivos</h1>

            <div>
                <DeviceList>
                    {isFetching ? (
                        <div>Carregando...</div>
                    ) : isError ? (
                        <div>Ocorreu um erro ao carregar os dados.</div>
                    ) : (
                        <>
                            {devices.data.map(item => (
                                <li key={item.id}><SensorCard id={item.id} name={item.name} time={item.time} value={item.data} onClick={handleSelectSensor}/></li> 
                            ))}
                        </>
                    )}

                </DeviceList>   

                <ControlPanel device={selectedSensorIndex > -1 ? devices.data[selectedSensorIndex] : null} 
                    handleOffline = {() => {devices.data[selectedSensorIndex].data == "offline" ? 
                        setOnlineMutation(selectedSensorIndex) : 
                        setOfflineMutation(selectedSensorIndex)}}
                    handlePause = {() => {setPauseMutation(selectedSensorIndex)}}
                    handleUnpause = {() => {setUnpauseMutation(selectedSensorIndex)}}
                >
                
                </ControlPanel>
            </div>
            
        </HomeContainer>

    )
}