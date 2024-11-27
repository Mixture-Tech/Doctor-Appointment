import React, { useState } from 'react';
import { FaArrowLeft, FaArrowRight } from 'react-icons/fa';
import DaLieu from '../../../assets/images/specialities/da-lieu.png';
import CotSong from '../../../assets/images/specialities/cot-song.png';
import ChamCuu from '../../../assets/images/specialities/cham-cuu.png';
import ChupXQuang from '../../../assets/images/specialities/chup-x-quang.png';
import CoXuongKhop from '../../../assets/images/specialities/co-xuong-khop.png';
import DiUngMienDich from '../../../assets/images/specialities/di-ung-mien-dich.png';
import ChanThuongChinhHinh from '../../../assets/images/specialities/chan-thuong-chinh-hinh.png';

export default function Specialization() {
    const allImages = [
        { src: ChamCuu, title: 'Châm cứu' },
        { src: ChanThuongChinhHinh, title: 'Chấn thương chỉnh hình' },
        { src: ChupXQuang, title: 'Chụp X-Quang' },
        { src: CotSong, title: 'Cột sống' },
        { src: CoXuongKhop, title: 'Cơ xương khớp' },
        { src: DaLieu, title: 'Da liễu' },
        { src: DiUngMienDich, title: 'Dị ứng miễn dịch' },
        { src: DiUngMienDich, title: 'Không Phải Dị ứng miễn dịch' },
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
        <section className="mb-20 mt-20">
            <div className="container mx-auto px-4">
                <h2 className="text-3xl font-bold text-center mb-10 text-primary-1000">
                    Chuyên khoa
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
                            grid grid-cols-4 gap-6
                            transform transition-all duration-500 ease-in-out
                            ${isAnimating ? 'opacity-50 scale-95' : 'opacity-100 scale-100'}
                        `}>
                            {visibleImages.map((image, index) => (
                                <div
                                    key={`${index}-${image.title}`}
                                    className={`
                                        bg-white shadow-2xl p-6 rounded-lg
                                        flex flex-col items-center text-center h-72
                                        transform transition-all duration-300
                                        hover:shadow-lg hover:-translate-y-1
                                        cursor-pointer
                                    `}
                                >
                                    <div className="h-40 w-full mb-4 overflow-hidden rounded">
                                        <img
                                            src={image.src}
                                            alt={`Specialization ${index + 1}`}
                                            className="w-full h-full object-cover rounded
                                                    transition-transform duration-300
                                                    hover:scale-110"
                                        />
                                    </div>
                                    <div className="flex-1 flex items-center">
                                        <h4 className="text-xl font-semibold line-clamp-2
                                                     transition-colors duration-300
                                                     hover:text-primary">
                                            {image.title}
                                        </h4>
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