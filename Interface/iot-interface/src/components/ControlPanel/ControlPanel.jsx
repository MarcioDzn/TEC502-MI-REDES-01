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
                                device.status !== "disconnected" ? 
                                <div>
                                    <i className="bi bi-wifi"></i>
                                    Conectado  
                                </div> : 
                                <div>
                                    <i className="bi bi-wifi-off"></i>
                                    Desconectado  
                                </div> 

                            }
                            <span>{device.status !== "disconnected" ? device.status : ""}</span>
                        </ControlPanelStatus>
                    </SensorInfo>
                    <hr />
                    <ControlButtons>
                        <PowerButton offline={device.status} disconnected={(device.status == "disconnected").toString()} onClick={handleOffline} disabled={device.status == "disconnected"}><i className="bi bi-power"></i></PowerButton>
                    </ControlButtons>                
                </>
                :
                <NoneSensor>Nenhum sensor selecionado</NoneSensor>
            }
            

        </ControlPanelContainer>
    )
}