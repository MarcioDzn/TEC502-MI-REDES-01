import { ButtonContainer } from "./ButtonStyled";

export function Button({handleClick, disabled, children}) {
    return (
        <ButtonContainer onClick={handleClick} disabled={disabled}>
            {children}
        </ButtonContainer>
        
    )
}