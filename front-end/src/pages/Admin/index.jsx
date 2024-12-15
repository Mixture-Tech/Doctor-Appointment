// src/pages/Dashboard.js
import React, { useState } from "react";
import Sidebar from "./components/Sidebar";
import Navbar from "./components/Navbar";
import Content from "./components/Content";

export default function Dashboard() {
    const [isSidebarOpen, setSidebarOpen] = useState(false);

    const toggleSidebar = () => {
        setSidebarOpen(!isSidebarOpen);
    };

    return (
        <div className="flex">
            <Sidebar isOpen={isSidebarOpen} />

            <div
                className={`transition-all duration-300 ${
                    isSidebarOpen ? "ml-64" : "ml-0"
                } flex-1`}
            >
                <Navbar toggleSidebar={toggleSidebar}/>
                <Content/>
            </div>
        </div>
    );
}
