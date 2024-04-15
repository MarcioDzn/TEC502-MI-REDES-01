import styled from "styled-components"

export const ControlPanelContainer = styled.section`
    width: 500px;
    background-color: white;
    height: 100%;
    padding: 20px;
    border-radius: 10px;
`

export const ControlPanelTitle = styled.h1`
    font-size: 1.9rem !important;
`

export const ControlPanelTime = styled.span`
    font-size: 1.2rem;
`

export const ControlPanelValue = styled.span`
    font-size: 3rem;
    font-weight: bold;
`

export const ControlPanelStatus = styled.span`
    display: flex;
    gap: 10px;
    font-size: 1.2rem;
`

export const SensorInfo = styled.div`
    display: flex;
    flex-direction: column;
    height: fit-content !important;

    div {
        display: flex;
        align-items: center;
        justify-content: space-between;
    }
`

export const ControlButtons = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;

    height: 200px;

    div {
        width: 100%;
        height: 40px;
    }
`

export const PowerButton = styled.button`
    width: 150px;
    height: 150px;
    background-color: white;
    border: 1px solid #e4e4e4;
    border-radius: 50%;
    transition: all 0.3s ease-in-out;
    margin: 40px 0px;

    box-shadow: ${(props) => props.disconnected ? "0px 0px 20px 1px #cecece" : props.offline == "offline" ? 
    `
    0px 0px 20px 1px #ff0000
    ` : "0px 0px 20px 1px green" };
    
    cursor: pointer;

    &:hover {
        box-shadow: ${(props) => props.disconnected ? "0px 0px 20px 1px #cecece" : props.offline == "offline" ? 
        `
        0px 0px 20px 5px #ff0000
        ` : "0px 0px 20px 5px green" };
        
        }

    i {
        font-size: 3rem;
    }
`

export const NoneSensor = styled.h1`
    font-size: 0.5rem;
    color: #dadada;
`
