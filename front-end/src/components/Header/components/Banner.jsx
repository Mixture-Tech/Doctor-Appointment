import React from 'react';
import Button from '../../Form/Button/index';

export default function Banner() {
  return (
    <div className="relative h-screen">
      <div
        className="absolute inset-0 bg-cover bg-center bg-no-repeat"
        style={{ backgroundImage: `url(${require('../../../assets/images/background.jpg')})` }}
      />
      <div className="relative z-10 h-full flex flex-col items-start justify-center text-primary-1000 px-12">
        <h1 className="text-lg font-bold mb-4">GIẢI PHÁP CHĂM SÓC SỨC KHỎE TOÀN DIỆN</h1>
        <h2 className="text-5xl font-semibold mb-2" style={{ lineHeight: '1.2' }}>NGƯỜI BẠN ĐỒNG HÀNH <br/>ĐÁNG TIN CẬY NHẤT VỀ SỨC KHOẺ</h2>
        <p className="text-sm text-lg mb-8 ">
          A repudiandae ipsam labore ipsa voluptatum quidem quae laudantium quisquam aperiam maiores sunt fugit,
          deserunt rem suscipit placeat.
        </p>
        <Button variant="secondary" onClick={() => console.log('Secondary button clicked')}>
          MAKE AN APPOINTMENT
        </Button>
      </div>
    </div>
  );
}
