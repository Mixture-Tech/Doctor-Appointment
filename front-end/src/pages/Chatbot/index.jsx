import React from 'react';
import Left from './components/Left';
import Right from './components/Right';

export default function Chatbot() {
    return (
        <div className="flex">
            <Left />
            <Right />
        </div>
    );
}
