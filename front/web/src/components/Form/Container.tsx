import React from "react";
import { Container as BootstrapContainer, Card } from "react-bootstrap";

interface ContainerProps extends React.HTMLAttributes<HTMLDivElement> {
    children: React.ReactNode;
}

export function Container({ children, ...rest }: ContainerProps) {
    return (
        <BootstrapContainer className="mt-5" style={{ maxWidth: '600px' }}{...rest}>
            <Card className="p-4 shadow-sm">
                {children}
            </Card>
        </BootstrapContainer>
    );
}
