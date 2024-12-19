import { Outlet, useLocation } from "react-router-dom";
import Sidebar from "../components/Form/Admin/Sidebar";  // Sidebar component
import Navbar from "../components/Form/Admin/Navbar";    // Navbar component
import {useEffect, useState} from "react";

export default function AdminLayout() {
    const location = useLocation();

    // Mặc định Sidebar mở khi vào trang
    const [isSidebarOpen, setSidebarOpen] = useState(true);

    // Hàm toggle Sidebar
    const toggleSidebar = () => {
        setSidebarOpen(prevState => !prevState);
    };

    // Hook useEffect để thực thi một lần khi component được render lần đầu
    useEffect(() => {
        // Sidebar mặc định sẽ mở khi vào trang
        setSidebarOpen(true);
    }, []);

    return (
        <div className="flex flex-col min-h-screen">
            {/* Navbar sẽ chứa nút hamburger để điều khiển Sidebar */}
            <div
                className={`transition-transform duration-300 ease-in-out ${isSidebarOpen ? 'ml-64' : 'ml-0'}`}
            >
                <Navbar toggleSidebar={toggleSidebar} />
            </div>

            <div className="flex flex-1">
                {/* Sidebar */}
                <Sidebar isOpen={isSidebarOpen} />

                {/* Nội dung chính, được đẩy sang phải khi Sidebar mở */}
                <div
                    className={`transition-transform duration-300 ease-in-out flex-1 p-4 ${isSidebarOpen ? 'ml-64' : 'ml-0'}`}
                >
                    <Outlet />
                </div>
            </div>
        </div>
    );
}
