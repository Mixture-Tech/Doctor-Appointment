import React from 'react';
import { RiEdit2Line } from "react-icons/ri";

export default function Left() {
    return (
        <div className="flex flex-col h-screen bg-gray-50 w-120">
            {/* Header */}
            <div className="flex items-center justify-between px-6 py-4 border-b border-gray-300 bg-white">
                <div className="flex items-center ml-auto">
                    <button className="text-gray-500 hover:text-gray-700 flex items-center">
                        <RiEdit2Line className="h-5 w-5" />
                    </button>
                </div>
            </div>

            {/* Sidebar Content */}
            <div className="flex-1 overflow-y-auto px-4 py-2">
                <h3 className="text-sm font-medium text-gray-500">Hôm nay</h3>
                <ul className="mt-2 space-y-1"> {/* Giảm khoảng cách giữa các dòng */}
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Java tính giai thừa</li>
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Fetch API hỗ trợ</li>
                </ul>

                <h3 className="mt-4 text-sm font-medium text-gray-500">7 ngày trước đó</h3>
                <ul className="mt-2 space-y-1"> {/* Giảm khoảng cách giữa các dòng */}
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Căn lề giữa đoạn mã</li>
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Danh sách bệnh lý</li>
                </ul>

                <h3 className="mt-4 text-sm font-medium text-gray-500">30 ngày trước đó</h3>
                <ul className="mt-2 space-y-1"> {/* Giảm khoảng cách giữa các dòng */}
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Kiểm tra câu lệnh SQL</li>
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Kiểm tra bệnh và nhóm</li>
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Danh sách chuyên khoa</li>
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Quản lý cho thuê văn phòng</li>
                </ul>
                <h3 className="mt-4 text-sm font-medium text-gray-500">35 ngày trước đó</h3>
                <ul className="mt-2 space-y-1"> {/* Giảm khoảng cách giữa các dòng */}
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Kiểm tra câu lệnh SQL</li>
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Kiểm tra bệnh và nhóm</li>
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Danh sách chuyên khoa</li>
                    <li className="px-4 py-2 rounded-md hover:bg-gray-200 cursor-pointer">Quản lý cho thuê văn phòng</li>
                </ul>
            </div>
        </div>
    );
}
