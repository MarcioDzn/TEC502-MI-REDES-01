import { ButtonContainer } from "./ButtonStyled";

export function Button({handleClick, children}) {
    return (
        <ButtonContainer onClick={handleClick}>
            {children}
        </ButtonContainer>
        
    )
}