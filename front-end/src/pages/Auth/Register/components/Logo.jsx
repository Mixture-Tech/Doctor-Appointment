import React from "react";
const logo = require("../../../../assets/images/logo.png");

export default function Logo() {
    return (
        <div className="flex justify-center mb-3">
            <img src={logo} alt="Logo" className="h-28" />
        </div>
    );
}
