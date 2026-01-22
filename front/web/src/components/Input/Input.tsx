import React from 'react';
import styles from './Input.module.css';

interface InputProp {
    title:string;
    value: string | number;
    placeholder?:string;
    setValue: any;
    required:boolean;
    type?:React.HTMLInputTypeAttribute;
    disabled?:boolean;
}

const Input: React.FC<InputProp> = ({
    title,
    value,
    placeholder,
    setValue,
    type,
    required,
    disabled
}) => {
    return(
      <div className={styles.inputGroup}>
        <label className={styles.label}>{title}</label>
        <input
          className={styles.input}
          placeholder={placeholder|| title}
          value={value}
          onChange={(e) => setValue(e.target.value)}
          required={required}
          type={type !== "" ? type : "text"}
          disabled={disabled !== null ? false : true}
        />
      </div>
    );
}

export default Input;