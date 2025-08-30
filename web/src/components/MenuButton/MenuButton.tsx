import React from "react";
import styles from "./MenuButton.module.css";

type Props = {
  label: string;
  onClick: () => void;
  color?: string;
};

export default function MenuButton({ label, onClick, color }: Props) {
  return (
    <button
      className={styles.button}
      onClick={onClick}
      style={{ backgroundColor: color || "#2a3a7f" }}
    >
      {label}
    </button>
  );
}
