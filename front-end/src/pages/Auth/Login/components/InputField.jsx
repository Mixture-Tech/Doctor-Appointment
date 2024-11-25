import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default function InputField({ label, type, placeholder, icon }) {
    return (
        <div className="mb-4">
            <label className="block text-gray-700 text-sm font-semibold mb-2">
                {label}
            </label>
            <div className="flex items-center border rounded-lg px-3 py-2 focus-within:ring-2 focus-within:ring-blue-500">
                <FontAwesomeIcon icon={icon} className="text-gray-500 mr-3" />
                <input
                    type={type}
                    placeholder={placeholder}
                    className="w-full focus:outline-none"
                />
            </div>
        </div>
    );
}
