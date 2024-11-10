import React, { useState } from 'react';
import { FaArrowLeft, FaArrowRight } from 'react-icons/fa';
import DangVanHa  from '../../../assets/images/doctors/dang-van-ha.png';
import DiecKhaHan from '../../../assets/images/doctors/diec-kha-han.png';
import BuiGiaLuong from '../../../assets/images/doctors/bui-gia-luong.png';
import BuiVanKhanh from '../../../assets/images/doctors/bui-van-khanh.png';
import DinhNgocSon from '../../../assets/images/doctors/dinh-ngoc-son.png';
import DoanTrucQuynh from '../../../assets/images/doctors/doan-truc-quynh.png';
import DinhThiThuHuong from '../../../assets/images/doctors/dinh-thi-thu-huong.png';


export default function Doctor() {
    const allImages = [
        { src: BuiGiaLuong, title: 'Bùi Gia Lượng', specialities: 'Châm cứu' },
        { src: BuiVanKhanh, title: 'Bùi Văn Khánh', specialities : 'Chấn thương chỉnh hình' },
        { src: DangVanHa, title: 'Đặng Văn Hà', specialities : 'Chụp X-Quang' },
        { src: DiecKhaHan, title: 'Diec Khả Hân', specialities : 'Cột sống' },
        { src: DinhNgocSon, title: 'Đinh Ngọc Sơn', specialities : 'Cơ xương khớp' },
        { src: DinhThiThuHuong, title: 'Đinh Thị Thu Hương', specialities : 'Da liễu' },
        { src: DoanTrucQuynh, title: 'Đoàn Trúc Quỳnh', specialities : 'Dị ứng miễn dịch' },
    ];

    const [startIndex, setStartIndex] = useState(0);
    const [isAnimating, setIsAnimating] = useState(false);

    const getCurrentImages = () => {
        const visibleImages = [];
        for (let i = 0; i < 4; i++) {
            const index = (startIndex + i) % allImages.length;
            visibleImages.push(allImages[index]);
        }
        return visibleImages;
    };

    const handlePrevClick = () => {
        if (isAnimating) return;
        setIsAnimating(true);
        setStartIndex((prevIndex) => {
            const newIndex = prevIndex - 3;
            return newIndex < 0 ? 0 : newIndex;
        });
        setTimeout(() => setIsAnimating(false), 500);
    };

    const handleNextClick = () => {
        if (isAnimating) return;
        setIsAnimating(true);
        setStartIndex((prevIndex) => {
            const newIndex = prevIndex + 3;
            return newIndex > allImages.length - 4 ? allImages.length - 4 : newIndex;
        });
        setTimeout(() => setIsAnimating(false), 500);
    };

    const visibleImages = getCurrentImages();

    return (
        <section className="mb-20 mt-20" style={{ backgroundImage: `url(${require('../../../assets/images/background-doctor.png')})` }}>
            <div className="container mx-auto px-4">
                <h2 className="text-3xl font-bold text-center mb-16 text-primary-1000 pt-5">
                    Bác sĩ của chúng tôi
                </h2>
                <div className="flex justify-between items-center">
                    <div className="w-8 flex justify-center">
                        {startIndex > 0 && (
                            <button
                                className="transform transition-all duration-300 hover:scale-110"
                                onClick={handlePrevClick}
                                disabled={isAnimating}
                            >
                                <FaArrowLeft className="text-2xl text-primary hover:text-primary-600 transition-colors duration-300" />
                            </button>
                        )}
                    </div>

                    <div className="flex-1 mx-4 overflow-hidden">
                        <div className={`
                            grid grid-cols-4 gap-8
                            transform transition-all duration-500 ease-in-out
                            ${isAnimating ? 'opacity-50 scale-95' : 'opacity-100 scale-100'}
                        `}>
                            {visibleImages.map((image, index) => (
                                <div
                                    key={`${index}-${image.title}`}
                                    className={`
                                        p-4 rounded-xl
                                        flex flex-col items-center text-center h-96
                                        transform transition-all duration-300
                                        hover:shadow-lg hover:-translate-y-2
                                        cursor-pointer
                                    `}
                                >
                                    <div className="h-60 w-60 mb-6 overflow-hidden rounded-full">
                                        <img
                                            src={image.src}
                                            alt={`Bác sĩ ${image.title}`}
                                            className="w-full h-full object-cover
                                                    transition-transform duration-300
                                                    hover:scale-110"
                                        />
                                    </div>
                                    <div className="flex-1">
                                        <h4 className="text-xl font-semibold mb-2
                                                     transition-colors duration-300
                                                     hover:text-primary">
                                            {image.title}
                                        </h4>
                                        <p className="text-lg text-gray-600
                                                     transition-colors duration-300
                                                     hover:text-primary">
                                            {image.specialities}
                                        </p>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>

                    <div className="w-8 flex justify-center">
                        {startIndex < allImages.length - 4 && (
                            <button
                                className="transform transition-all duration-300 hover:scale-110"
                                onClick={handleNextClick}
                                disabled={isAnimating}
                            >
                                <FaArrowRight className="text-2xl text-primary hover:text-primary-600 transition-colors duration-300" />
                            </button>
                        )}
                    </div>
                </div>
            </div>
        </section>
    );
}