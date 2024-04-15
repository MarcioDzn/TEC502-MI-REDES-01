import { Button } from "../Button/Button";
import { ControlButtons, ControlPanelContainer, ControlPanelStatus, ControlPanelTime, ControlPanelTitle, ControlPanelValue, NoneSensor, PowerButton, SensorInfo } from "./ControlPanelStyled";

export function ControlPanel({device, handleOffline, handlePause, handleUnpause, children}) {
    return (
        <ControlPanelContainer>
            {children}
            {
                device ? 
                <>
                    <SensorInfo>
                        <div>
                            <ControlPanelTitle>{device.name}</ControlPanelTitle>
                            <ControlPanelTime>{device.time}</ControlPanelTime>
                        </div>

                        <ControlPanelValue>{device.data}</ControlPanelValue>
                        <ControlPanelStatus>
                        {
                            device.status === "online" ? 
                            <>
                                <i className="bi bi-wifi"></i>
                                Conectado  
                            </> : 
                            <>
                                <i className="bi bi-wifi-off"></i>
                                Desconectado  
                            </> 

                        }
                        </ControlPanelStatus>
                    </SensorInfo>
                    <hr />
                    <ControlButtons>
                        <PowerButton offline={device.data} disconnected={device.status == "offline"} onClick={handleOffline} disabled={device.status == "offline"}><i className="bi bi-power"></i></PowerButton>

                        <div>
                            <Button handleClick={handlePause} disabled={device.status == "offline"}><i className="bi bi-pause-fill"></i></Button>
                            <Button handleClick={handleUnpause} disabled={device.status == "offline"}><i className="bi bi-play-fill"></i></Button>
                        </div>
                    </ControlButtons>                
                </>
                :
                <NoneSensor>Nenhum sensor selecionado</NoneSensor>
            }
            

        </ControlPanelContainer>
    )
}