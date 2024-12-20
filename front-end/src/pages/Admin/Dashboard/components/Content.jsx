// src/components/DashboardContent.js
import React from "react";

export default function Content() {
    return (
        <div className="p-6 space-y-6">
            {/* Header Section */}
            <div className="flex flex-row">
                <div className="flex flex-col w-2/3 items-start justify-between bg-blue-100 rounded-lg p-6 shadow">
                    <div>
                        <h1 className="text-xl font-bold text-blue-700">
                            Chào buổi sáng, Trần Gia Bảo
                        </h1>
                        <p className="text-gray-500 mb-16">Lịch làm việc hôm nay</p>
                        <div className="flex mt-4 space-x-4">
                            <div className="bg-white p-4 rounded shadow">
                                <p className="text-gray-500">Tổng số cuộc hẹn</p>
                                <p className="flex justify-center text-blue-500 text-2xl font-bold">6</p>
                            </div>
                            {/* <div className="bg-white p-4 rounded shadow">
                                <p className="text-gray-500"></p>
                                <p className="flex justify-center text-green-500 text-2xl font-bold">3</p>
                            </div> */}
                        </div>
                    </div>

                </div>
                {/* Activity Section */}
                <div className="flex flex-col w-1/3 bg-green-100 p-4 rounded-lg shadow ml-6">
                    <h2 className="text-lg font-semibold mb-4">Hoạt động</h2>
                    <div className="flex flex-col items-center">
                        <div className="w-full flex justify-between text-sm mb-2">
                            <p>S</p>
                            <p>M</p>
                            <p>T</p>
                            <p>W</p>
                            <p>T</p>
                            <p>F</p>
                            <p>S</p>
                        </div>
                        <div className="w-full bg-white h-32 rounded shadow">
                            <div className="flex h-full">
                                <div className="w-1/7 bg-green-300 m-1"></div>
                                <div className="w-1/7 bg-green-400 m-1"></div>
                                <div className="w-1/7 bg-green-200 m-1"></div>
                                <div className="w-1/7 bg-green-500 m-1"></div>
                                <div className="w-1/7 bg-green-300 m-1"></div>
                                <div className="w-1/7 bg-green-400 m-1"></div>
                                <div className="w-1/7 bg-green-200 m-1"></div>
                            </div>
                        </div>
                        <p className="mt-2 text-sm text-gray-500">
                            60% bệnh nhân cao hơn tuần trước.
                        </p>
                    </div>
                </div>
            </div>

            {/* Main Content */}
            <div className="flex mt-6 space-x-6">
                {/* Available Doctors */}
                <div className="flex-1 bg-white p-4 rounded-lg shadow">
                    <h2 className="text-lg font-semibold mb-4">Bác sĩ được đề xuất</h2>
                    <div className="flex space-x-4">
                        <div className="flex items-center space-x-2 bg-gray-100 p-4 rounded-lg shadow">
                            <img
                                src="https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/eula/image.png?strip=all&quality=75&w=256"
                                alt="Doctor"
                                className="w-12 h-12 rounded-full"
                            />
                            <div>
                                <p className="font-bold">Nguyễn Văn Hùng</p>
                                <p className="text-gray-500 text-sm">Thần kinh</p>
                                <p className="text-yellow-400">★★★★★</p>
                            </div>
                        </div>
                        <div className="flex items-center space-x-2 bg-gray-100 p-4 rounded-lg shadow">
                            <img
                                src="https://i2.wp.com/genshinbuilds.aipurrjects.com/genshin/characters/yae_miko/image.png?strip=all&quality=75&w=256"
                                alt="Doctor"
                                className="w-12 h-12 rounded-full"
                            />
                            <div>
                                <p className="font-bold">Trần Quang Huy</p>
                                <p className="text-gray-500 text-sm">Tim mạch</p>
                                <p className="text-yellow-400">★★★★☆</p>
                            </div>
                        </div>
                    </div>
                </div>
                {/* Upcoming Surgeries */}
                <div className="bg-white p-4 rounded-lg shadow">
                    <h2 className="text-lg font-semibold mb-4">Upcoming Surgeries</h2>
                    <div className="flex space-x-4">
                        <div className="flex flex-col bg-gray-100 p-4 rounded-lg shadow">
                            <p className="text-blue-500 font-bold">Thu 23</p>
                            <p className="font-bold mt-1">Amelia Bruklin</p>
                            <p className="text-gray-500 text-sm">Neurologist</p>
                            <span className="mt-2 text-blue-600 text-sm font-semibold">2:30 PM</span>
                        </div>
                        <div className="flex flex-col bg-gray-100 p-4 rounded-lg shadow">
                            <p className="text-green-500 font-bold">Sat 25</p>
                            <p className="font-bold mt-1">Bhottan Cozei</p>
                            <p className="text-gray-500 text-sm">Surgeon</p>
                            <span className="mt-2 text-green-600 text-sm font-semibold">5:00 PM</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}
