// Button.js
import React from 'react';

export default function Button({ onClick, children }) {
  return (
    <button
      onClick={onClick}
      className="rounded-full px-6 py-3 border bg-primary hover:bg-primary-200 text-primary-1000 hover:text-primary-1000 transition duration-300 rounded"
    >
      {children}
    </button>
  );
}
