import React from "react";
import { Button as Btn} from "react-bootstrap";

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
    children:string;
}

export function Button({ children, ...rest }: ButtonProps) {
    return <Btn variant = "primary" {...rest}>{children}</Btn>;
}