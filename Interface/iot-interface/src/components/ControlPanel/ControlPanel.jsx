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
                            <ControlPanelTitle>{device?.name}</ControlPanelTitle>
                            <ControlPanelTime>{device?.time}</ControlPanelTime>
                        </div>

                        <ControlPanelValue>{device.data}</ControlPanelValue>
                        <ControlPanelStatus>
                            <i className="bi bi-wifi"></i>
                            Conectado 
                        </ControlPanelStatus>
                    </SensorInfo>
                    <hr />
                    <ControlButtons>
                        <PowerButton offline={device.data} onClick={handleOffline}><i className="bi bi-power"></i></PowerButton>

                        <div>
                            <Button handleClick={handlePause}><i className="bi bi-pause-fill"></i></Button>
                            <Button handleClick={handleUnpause}><i className="bi bi-play-fill"></i></Button>
                        </div>
                    </ControlButtons>                
                </>
                :
                <NoneSensor>Nenhum sensor selecionado</NoneSensor>
            }
            

        </ControlPanelContainer>
    )
}