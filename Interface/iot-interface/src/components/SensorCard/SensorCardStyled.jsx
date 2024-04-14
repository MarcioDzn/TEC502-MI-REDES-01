import styled from "styled-components"

export const CardContainer = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-direction: column;
    width: 200px;
    height: 150px;
    background-color: white;
    box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;
    padding: 10px;
    border-radius: 10px;
    cursor: pointer;
    
`

export const CardInfos = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
`

export const CardTitle = styled.span`
    font-size: 0.9rem;
    font-weight: bold;
`

export const CardTime = styled.span`
    font-size: 0.7rem;
`

export const CardValue = styled.span`
    font-size: 3rem;
    font-weight: bold;
`

export const CardStatus = styled.span`
    display: flex;
    gap: 5px;
    align-items: start;
    width: 100%;
    font-size: 0.8rem;
`