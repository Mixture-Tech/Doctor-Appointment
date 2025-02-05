import React, { useState } from "react";
import Sidebar from "../../../components/Form/Admin/Sidebar";
import Content from "./components/Content";
import { ToastContainer } from "react-toastify";

export default function Dashboard() {
    const [isSidebarOpen, setSidebarOpen] = useState(false);

    const toggleSidebar = () => {
        setSidebarOpen(!isSidebarOpen);
    };

    return (
        <div className="flex">
            <ToastContainer style={{marginTop: '50px'}}/>
            <Sidebar isOpen={isSidebarOpen} />

            <div
                className={`transition-all duration-300 ${
                    isSidebarOpen ? "ml-64" : "ml-0"
                } flex-1`}
            >
                <Content />
            </div>
        </div>
    );
}
