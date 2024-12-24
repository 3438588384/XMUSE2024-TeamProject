/*
 Navicat MySQL Data Transfer

 Source Server         : 1761
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : hw_book_sys

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 04/07/2023 18:56:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory`  (
  `product_id` int(11) NOT NULL,
  `stock` int(11) NOT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inventory
-- ----------------------------
INSERT INTO `inventory` VALUES (1, 100, '充足');
INSERT INTO `inventory` VALUES (2, 100, '充足');
INSERT INTO `inventory` VALUES (3, 0, '无');
INSERT INTO `inventory` VALUES (4, 0, '无');
INSERT INTO `inventory` VALUES (5, 0, '无');
INSERT INTO `inventory` VALUES (6, 0, '无');
INSERT INTO `inventory` VALUES (7, 0, '无');
INSERT INTO `inventory` VALUES (11, 0, '无');
INSERT INTO `inventory` VALUES (12, 0, '无');
INSERT INTO `inventory` VALUES (13, 0, '无');
-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `price` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `status` varchar(5) NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, '可乐', '4', '饮料', '1');
INSERT INTO `product` VALUES (2, '老坛泡面', '5', '面食', '1');
INSERT INTO `product` VALUES (3, '乐事薯片', '6', '零食', '1');
INSERT INTO `product` VALUES (4, '三得利乌龙茶', '5.5', '饮料', '0');
INSERT INTO `product` VALUES (5, '青柑普洱', '5', '饮料', '1');
INSERT INTO `product` VALUES (6, '薯条', '7', '零食', '0');
INSERT INTO `product` VALUES (7, '伯牙绝弦', '20', '饮料', '1');
INSERT INTO `product` VALUES (11, '水', '2', '饮食', '0');
INSERT INTO `product` VALUES (12, '炸鸡', '12', '食物', '0');
INSERT INTO `product` VALUES (13, '冰淇淋', '3', '甜点', '1');
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '1', '1');
INSERT INTO `user` VALUES (2, '2', '2');
INSERT INTO `user` VALUES (3, '3', '3');

-- ----------------------------
-- Table structure for ProductImage
-- ----------------------------
DROP TABLE IF EXISTS `ProductImage`;
CREATE TABLE `ProductImage`  (
    `img_id` int(11) NOT NULL AUTO_INCREMENT,
    `product_id` int(11) NOT NULL,
    `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    PRIMARY KEY (`img_id`) USING BTREE,
    CONSTRAINT `fk_image` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

INSERT INTO `ProductImage` VALUES (1, 1, 'src/image/cola.jpg');
INSERT INTO `ProductImage` VALUES (2, 2, 'src/image/Instant_noodle.jpg');
INSERT INTO `ProductImage` VALUES (3, 3, 'src/image/lays_potato_chips.jpg');
INSERT INTO `ProductImage` VALUES (4, 4, 'src/image/Suntory_Oolong_Tea.jpg');
INSERT INTO `ProductImage` VALUES (5, 5, 'src/image/Aged_Tangerine_Puerh.jpg');
INSERT INTO `ProductImage` VALUES (6, 6, 'src/image/french_fries.jpg');
INSERT INTO `ProductImage` VALUES (7, 7, 'src/image/jasmine_green_milk_tea.jpg');
-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    CONSTRAINT `fk_user` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` (`username`, `password`) VALUES ('1', '1');

-- ----------------------------
-- Table structure for product_on_shelf
-- ----------------------------
DROP TABLE IF EXISTS `product_on_shelf`;
CREATE TABLE `product_on_shelf` (
    `id` int(11) NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `price` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `shelf_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_product1` FOREIGN KEY (`id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=Dynamic;

INSERT INTO product_on_shelf (id, name, price, category) VALUES (1, '可乐', '4', '饮料');
INSERT INTO product_on_shelf (id, name, price, category) VALUES (2, '老坛泡面', '5', '面食');
INSERT INTO product_on_shelf (id, name, price, category) VALUES (3, '乐事薯片', '6', '零食');
INSERT INTO product_on_shelf (id, name, price, category) VALUES (5, '青柑普洱', '5', '饮料');
INSERT INTO product_on_shelf (id, name, price, category) VALUES (7, '伯牙绝弦', '20', '饮料');
INSERT INTO product_on_shelf (id, name, price, category) VALUES (13, '冰淇淋', '3', '甜点');

-- ----------------------------
-- Table structure for product_off_shelf
-- ----------------------------
DROP TABLE IF EXISTS `product_off_shelf`;
CREATE TABLE `product_off_shelf` (
    `id` int(11) NOT NULL,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `price` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `shelf_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_product2` FOREIGN KEY (`id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB CHARACTER SET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=Dynamic;

INSERT INTO product_off_shelf (id, name, price, category) VALUES (4, '三得利乌龙茶', '5.5', '饮料');
INSERT INTO product_off_shelf (id, name, price, category) VALUES (6, '薯条', '7', '零食');
INSERT INTO product_off_shelf (id, name, price, category) VALUES (11, '水', '2', '饮食');
INSERT INTO product_off_shelf (id, name, price, category) VALUES (12, '炸鸡', '12', '食物');
-- ----------------------------
-- Trigger structure for product insert
-- ----------------------------
DELIMITER //
DROP TRIGGER IF EXISTS `after_product_insert`;
CREATE TRIGGER after_product_insert
    AFTER INSERT ON product
    FOR EACH ROW
BEGIN
    IF NEW.status = '1' THEN
        INSERT INTO product_on_shelf (id, name, price, category, shelf_date)
        VALUES (NEW.id, NEW.name, NEW.price, NEW.category, NOW())
        ON DUPLICATE KEY UPDATE name = NEW.name, price = NEW.price, category = NEW.category, shelf_date = NOW();
    ELSEIF NEW.status = '0' THEN
        INSERT INTO product_off_shelf (id, name, price, category, shelf_date)
        VALUES (NEW.id, NEW.name, NEW.price, NEW.category, NOW())
        ON DUPLICATE KEY UPDATE name = NEW.name, price = NEW.price, category = NEW.category, shelf_date = NOW();
END IF;
END //
-- ----------------------------
-- Trigger structure for product update
-- ----------------------------
-- 修改product表
DROP TRIGGER IF EXISTS `after_product_update`;
CREATE TRIGGER after_product_update
    AFTER UPDATE ON product
    FOR EACH ROW
BEGIN
    -- 如果之前的status='0' and 现在的status='1'
    IF OLD.status = '0' AND NEW.status = '1' THEN
        INSERT INTO product_on_shelf (id, name, price, category, shelf_date)
        VALUES (NEW.id, NEW.name, NEW.price, NEW.category, NOW())
        ON DUPLICATE KEY UPDATE name = NEW.name, price = NEW.price, category = NEW.category, shelf_date = NOW();
    DELETE FROM product_off_shelf WHERE id = NEW.id;

    -- 如果之前的status='1' and 现在的status='0'
    ELSEIF OLD.status = '1' AND NEW.status = '0' THEN
        INSERT INTO product_off_shelf (id, name, price, category, shelf_date)
        VALUES (NEW.id, NEW.name, NEW.price, NEW.category, NOW())
        ON DUPLICATE KEY UPDATE name = NEW.name, price = NEW.price, category = NEW.category, shelf_date = NOW();
    DELETE FROM product_on_shelf WHERE id = NEW.id;

    -- 如果之前的status=之后的status and status='1'
    ELSEIF OLD.status = NEW.status AND NEW.status = '1' THEN
    UPDATE product_on_shelf
    SET name = NEW.name, price = NEW.price, category = NEW.category
    WHERE id = NEW.id;

    -- 如果之前的status=之后的status and status='0'
    ELSEIF OLD.status = NEW.status AND NEW.status = '0' THEN
    UPDATE product_off_shelf
    SET name = NEW.name, price = NEW.price, category = NEW.category
    WHERE id = NEW.id;
END IF;
END //
DELIMITER ;
-- ----------------------------
-- Trigger structure for user update
-- ----------------------------
DELIMITER //

CREATE TRIGGER after_user_update
    AFTER UPDATE ON user
    FOR EACH ROW
BEGIN
    UPDATE admin
    SET username = NEW.username,
        password = NEW.password
    WHERE id = NEW.id;
END //

DELIMITER ;

