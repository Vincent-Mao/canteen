/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80044
 Source Host           : localhost:3306
 Source Schema         : canteen

 Target Server Type    : MySQL
 Target Server Version : 80044
 File Encoding         : 65001

 Date: 15/04/2026 17:09:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`  (
  `banner_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '轮播图ID',
  `dishes_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜品ID',
  `sort` int NOT NULL COMMENT '排序',
  PRIMARY KEY (`banner_id`) USING BTREE,
  UNIQUE INDEX `banner_id`(`banner_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '轮播图' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of banner
-- ----------------------------
INSERT INTO `banner` VALUES ('5c1aafeb66b84213bcb63f06cc8d50be', '6a5a81d7e9274f12bf601bb5554d17c7', 3);
INSERT INTO `banner` VALUES ('d21fcd945e2244c5a2c77e043ab66b76', '6a5a81d7e9274f12bf601bb5554d17c1', 1);
INSERT INTO `banner` VALUES ('e6bc03d8b9df4fbe9cab0b960b22825b', '6a5a81d7e9274f12bf601bb5554d17c6', 2);

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `cart_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '购物车ID',
  `dishes_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜品ID',
  `quantity` int NOT NULL DEFAULT 1 COMMENT '数量',
  `user_id` int NOT NULL COMMENT '用户ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入购物车时间',
  PRIMARY KEY (`cart_id`) USING BTREE,
  UNIQUE INDEX `cart_id`(`cart_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '购物车' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of cart
-- ----------------------------

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '分类ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '分类名称',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '图片',
  `sort` int NOT NULL COMMENT '排序',
  PRIMARY KEY (`category_id`) USING BTREE,
  UNIQUE INDEX `category_id`(`category_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜品分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('178100bf9f3c478e8822985cb36b34ef', '饮品类', '/profile/upload/2026/04/15/饮品类_20260415165903A005.png', 5);
INSERT INTO `category` VALUES ('514c5d12fcad48df86e795251724379d', '素菜类', '/profile/upload/2026/04/15/素菜类_20260415165848A003.png', 3);
INSERT INTO `category` VALUES ('5f1c8287cb9144d4ab9e039cdd2d062a', '甜品类', '/profile/upload/2026/04/15/甜品类_20260415165914A006.png', 6);
INSERT INTO `category` VALUES ('6a5a81d7e9274f12bf601bb5554d17b5', '荤菜类', '/profile/upload/2026/04/15/荤菜类_20260415165841A002.png', 2);
INSERT INTO `category` VALUES ('7776bc52d2074588910a768f9a7b909f', '汤羹类', '/profile/upload/2026/04/15/汤羹类_20260415165857A004.png', 4);
INSERT INTO `category` VALUES ('c49cfab2ced6480087d4e9be22defe9a', '主食类', '/profile/upload/2026/04/15/主食类_20260415165831A001.png', 1);

-- ----------------------------
-- Table structure for collect
-- ----------------------------
DROP TABLE IF EXISTS `collect`;
CREATE TABLE `collect`  (
  `collect_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '收藏ID',
  `dishes_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜品ID',
  `user_id` int NOT NULL COMMENT '收藏用户ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
  PRIMARY KEY (`collect_id`) USING BTREE,
  UNIQUE INDEX `collect_id`(`collect_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '收藏' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of collect
-- ----------------------------
INSERT INTO `collect` VALUES ('3f67844d2dce4500b5cddd67d5b25c1a', '6a5a81d7e9274f12bf601bb5554d17b2', 103, '2025-11-20 13:44:46');
INSERT INTO `collect` VALUES ('ad1617e01da8401f88c6027e2fcfa088', '6a5a81d7e9274f12bf601bb5554d17c8', 1, '2025-11-20 12:46:57');
INSERT INTO `collect` VALUES ('e215eb068e3f4f1eaa1e76f98904d32f', '6a5a81d7e9274f12bf601bb5554d17b1', 103, '2025-11-20 13:44:45');

-- ----------------------------
-- Table structure for dishes
-- ----------------------------
DROP TABLE IF EXISTS `dishes`;
CREATE TABLE `dishes`  (
  `dishes_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜品ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜品名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '描述',
  `price` decimal(10, 2) NOT NULL COMMENT '价格',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '图片',
  `category_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '分类ID',
  PRIMARY KEY (`dishes_id`) USING BTREE,
  UNIQUE INDEX `dishes_id`(`dishes_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of dishes
-- ----------------------------
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17b1', '红烧牛肉面', '精选牛腩肉，慢炖入味，搭配劲道面条', 18.80, '/profile/upload/2026/04/15/红烧牛肉面_20260415165927A007.png', 'c49cfab2ced6480087d4e9be22defe9a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17b2', '西红柿鸡蛋面', '家常美味，酸甜可口，营养丰富', 12.00, '/profile/upload/2026/04/15/西红柿鸡蛋面_20260415165934A008.png', 'c49cfab2ced6480087d4e9be22defe9a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17b3', '扬州炒饭', '米饭粒粒分明，配料丰富', 10.00, '/profile/upload/2026/04/15/扬州炒饭_20260415165940A009.png', 'c49cfab2ced6480087d4e9be22defe9a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17b4', '炸酱面', '传统北京风味，酱香浓郁', 15.00, '/profile/upload/2026/04/15/炸酱面_20260415165947A010.png', 'c49cfab2ced6480087d4e9be22defe9a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17b5', '担担面', '四川特色，麻辣鲜香', 16.80, '/profile/upload/2026/04/15/担担面_20260415165954A011.png', 'c49cfab2ced6480087d4e9be22defe9a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17b6', '葱油拌面', '简单美味，葱香四溢', 8.80, '/profile/upload/2026/04/15/葱油拌面_20260415170000A012.png', 'c49cfab2ced6480087d4e9be22defe9a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17b7', '牛肉炒河粉', '河粉Q弹，牛肉鲜嫩', 20.00, '/profile/upload/2026/04/15/牛肉炒河粉_20260415170007A013.png', 'c49cfab2ced6480087d4e9be22defe9a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17b8', '水饺', '皮薄馅大，鲜美多汁', 12.00, '/profile/upload/2026/04/15/水饺_20260415170013A014.png', 'c49cfab2ced6480087d4e9be22defe9a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17b9', '宫保鸡丁', '经典川菜，鸡肉鲜嫩，花生香脆', 16.50, '/profile/upload/2026/04/15/宫保鸡丁_20260415170021A015.png', '6a5a81d7e9274f12bf601bb5554d17b5');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17c1', '麻辣香锅', '多种食材搭配，麻辣鲜香，回味无穷', 25.00, '/profile/upload/2026/04/15/麻辣香锅_20260415170028A016.png', '6a5a81d7e9274f12bf601bb5554d17b5');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17c2', '鱼香肉丝', '传统川菜，肉丝嫩滑，酸甜微辣', 19.80, '/profile/upload/2026/04/15/鱼香肉丝_20260415170035A017.png', '6a5a81d7e9274f12bf601bb5554d17b5');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17c3', '麻婆豆腐', '嫩滑豆腐配以麻辣肉糜，下饭佳品', 13.50, '/profile/upload/2026/04/15/麻婆豆腐_20260415170041A018.png', '6a5a81d7e9274f12bf601bb5554d17b5');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17c4', '糖醋里脊', '外酥里嫩，酸甜适口', 22.00, '/profile/upload/2026/04/15/糖醋里脊_20260415170047A019.png', '6a5a81d7e9274f12bf601bb5554d17b5');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17c5', '红烧排骨', '肉质软烂，酱香浓郁', 28.00, '/profile/upload/2026/04/15/红烧排骨_20260415170055A020.png', '6a5a81d7e9274f12bf601bb5554d17b5');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17c6', '回锅肉', '川菜代表，肥而不腻', 24.00, '/profile/upload/2026/04/15/回锅肉_20260415170103A021.png', '6a5a81d7e9274f12bf601bb5554d17b5');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17c7', '水煮鱼', '麻辣鲜香，鱼肉嫩滑', 35.00, '/profile/upload/2026/04/15/水煮鱼_20260415170112A022.png', '6a5a81d7e9274f12bf601bb5554d17b5');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17c8', '清炒时蔬', '新鲜时令蔬菜，清淡健康', 8.00, '/profile/upload/2026/04/15/清炒时蔬_20260415170120A023.png', '514c5d12fcad48df86e795251724379d');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17c9', '麻酱拌黄瓜', '清爽可口，麻酱香浓', 6.00, '/profile/upload/2026/04/15/麻酱拌黄瓜_20260415170126A024.png', '514c5d12fcad48df86e795251724379d');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17d1', '蒜蓉西兰花', '营养丰富，蒜香浓郁', 9.00, '/profile/upload/2026/04/15/蒜蓉西兰花_20260415170135A025.png', '514c5d12fcad48df86e795251724379d');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17d2', '地三鲜', '东北特色，土豆茄子青椒', 12.00, '/profile/upload/2026/04/15/地三鲜_20260415170142A026.png', '514c5d12fcad48df86e795251724379d');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17d3', '酸辣土豆丝', '酸辣开胃，口感爽脆', 7.00, '/profile/upload/2026/04/15/酸辣土豆丝_20260415170245A027.png', '514c5d12fcad48df86e795251724379d');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17d4', '干煸豆角', '香辣可口，豆角干香', 10.00, '/profile/upload/2026/04/15/干煸豆角_20260415170252A028.png', '514c5d12fcad48df86e795251724379d');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17d5', '蚝油生菜', '简单快手，鲜嫩爽口', 6.50, '/profile/upload/2026/04/15/蚝油生菜_20260415170259A029.png', '514c5d12fcad48df86e795251724379d');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17d6', '凉拌木耳', '爽脆可口，营养丰富', 5.00, '/profile/upload/2026/04/15/凉拌木耳_20260415170305A030.png', '514c5d12fcad48df86e795251724379d');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17d7', '冬瓜排骨汤', '清淡滋补，营养丰富', 8.00, '/profile/upload/2026/04/15/冬瓜排骨汤_20260415170312A031.png', '7776bc52d2074588910a768f9a7b909f');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17d8', '西红柿鸡蛋汤', '家常美味，酸甜开胃', 5.00, '/profile/upload/2026/04/15/西红柿鸡蛋汤_20260415170319A032.png', '7776bc52d2074588910a768f9a7b909f');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17d9', '紫菜蛋花汤', '简单快手，鲜美可口', 4.00, '/profile/upload/2026/04/15/紫菜蛋花汤_20260415170325A033.png', '7776bc52d2074588910a768f9a7b909f');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17e1', '酸辣汤', '酸辣开胃，配料丰富', 6.00, '/profile/upload/2026/04/15/酸辣汤_20260415170332A034.png', '7776bc52d2074588910a768f9a7b909f');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17e2', '玉米排骨汤', '清甜鲜美，营养滋补', 12.00, '/profile/upload/2026/04/15/玉米排骨汤_20260415170339A035.png', '7776bc52d2074588910a768f9a7b909f');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17e3', '鱼头豆腐汤', '汤白味鲜，营养丰富', 18.00, '/profile/upload/2026/04/15/鱼头豆腐汤_20260415170348A036.png', '7776bc52d2074588910a768f9a7b909f');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17e4', '鸡汤', '原汁原味，滋补养生', 15.00, '/profile/upload/2026/04/15/鸡汤_20260415170355A037.png', '7776bc52d2074588910a768f9a7b909f');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17e5', '海鲜豆腐汤', '鲜香美味，营养均衡', 16.00, '/profile/upload/2026/04/15/海鲜豆腐汤_20260415170437A038.png', '7776bc52d2074588910a768f9a7b909f');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17e6', '可乐', '冰镇可乐，畅快淋漓', 3.00, '/profile/upload/2026/04/15/可乐_20260415170443A039.png', '178100bf9f3c478e8822985cb36b34ef');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17e7', '雪碧', '清爽柠檬味，解渴佳品', 3.00, '/profile/upload/2026/04/15/雪碧_20260415170448A040.png', '178100bf9f3c478e8822985cb36b34ef');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17e8', '橙汁', '鲜榨橙汁，维生素丰富', 8.00, '/profile/upload/2026/04/15/橙汁_20260415170455A041.png', '178100bf9f3c478e8822985cb36b34ef');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17e9', '酸梅汤', '传统饮品，酸甜解腻', 5.00, '/profile/upload/2026/04/15/酸梅汤_20260415170502A042.png', '178100bf9f3c478e8822985cb36b34ef');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17f1', '奶茶', '香浓丝滑，多种口味', 12.00, '/profile/upload/2026/04/15/奶茶_20260415170510A043.png', '178100bf9f3c478e8822985cb36b34ef');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17f2', '柠檬水', '清新爽口，解渴消暑', 4.00, '/profile/upload/2026/04/15/柠檬水_20260415170520A044.png', '178100bf9f3c478e8822985cb36b34ef');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17f3', '豆浆', '营养健康，传统饮品', 3.00, '/profile/upload/2026/04/15/豆浆_20260415170526A045.png', '178100bf9f3c478e8822985cb36b34ef');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17f4', '啤酒', '冰镇啤酒，麦香浓郁', 6.00, '/profile/upload/2026/04/15/啤酒_20260415170534A046.png', '178100bf9f3c478e8822985cb36b34ef');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17f5', '提拉米苏', '意大利经典，咖啡酒香', 18.00, '/profile/upload/2026/04/15/提拉米苏_20260415170541A047.png', '5f1c8287cb9144d4ab9e039cdd2d062a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17f6', '芒果布丁', '香甜爽滑，果香浓郁', 12.00, '/profile/upload/2026/04/15/芒果布丁_20260415170548A048.png', '5f1c8287cb9144d4ab9e039cdd2d062a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17f7', '巧克力蛋糕', '浓郁巧克力，口感绵密', 15.00, '/profile/upload/2026/04/15/巧克力蛋糕_20260415170555A049.png', '5f1c8287cb9144d4ab9e039cdd2d062a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17f8', '冰淇淋', '多种口味，冰爽甜蜜', 8.00, '/profile/upload/2026/04/15/冰淇淋_20260415170601A050.png', '5f1c8287cb9144d4ab9e039cdd2d062a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d17f9', '水果沙拉', '新鲜水果，健康美味', 10.00, '/profile/upload/2026/04/15/水果沙拉_20260415170607A051.png', '5f1c8287cb9144d4ab9e039cdd2d062a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d18a1', '红豆汤圆', '软糯香甜，传统甜品', 6.00, '/profile/upload/2026/04/15/红豆汤圆_20260415170613A052.png', '5f1c8287cb9144d4ab9e039cdd2d062a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d18a2', '芝士蛋糕', '香浓芝士，口感细腻', 16.00, '/profile/upload/2026/04/15/芝士蛋糕_20260415170618A053.png', '5f1c8287cb9144d4ab9e039cdd2d062a');
INSERT INTO `dishes` VALUES ('6a5a81d7e9274f12bf601bb5554d18a3', '杨枝甘露', '港式甜品，果香浓郁', 14.00, '/profile/upload/2026/04/15/杨枝甘露_20260415170624A054.png', '5f1c8287cb9144d4ab9e039cdd2d062a');

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `feedback_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '意见反馈ID',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '反馈类型',
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系方式',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '反馈内容',
  `user_id` int NOT NULL COMMENT '反馈用户ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '反馈时间',
  PRIMARY KEY (`feedback_id`) USING BTREE,
  UNIQUE INDEX `feedback_id`(`feedback_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '意见反馈' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES ('8a036087aedd45eea4048ffd116c3062', '表扬鼓励', '13699998888', '二窗口的师傅特别有耐心, 非常感谢', 103, '2025-11-23 13:45:35');

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `table_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '表描述',
  `sub_table_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '关联子表的表名',
  `sub_table_fk_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '子表关联的外键名',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `tpl_web_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '前端模板类型（element-ui模版 element-plus模版）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成功能作者',
  `gen_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '生成代码方式（0zip压缩包 1自定义路径）',
  `gen_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '/' COMMENT '生成路径（不填默认项目路径）',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '代码生成业务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table
-- ----------------------------
INSERT INTO `gen_table` VALUES (17, 'banner', '轮播图', NULL, NULL, 'Banner', 'crud', 'element-plus', 'com.fast.canteen', 'canteen', 'banner', '轮播图', 'fast', '0', '/', '{}', 'admin', '2025-11-23 14:26:37', '', '2025-11-23 14:26:57', NULL);

-- ----------------------------
-- Table structure for gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_column`;
CREATE TABLE `gen_table_column`  (
  `column_id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` bigint NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '代码生成业务表字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gen_table_column
-- ----------------------------
INSERT INTO `gen_table_column` VALUES (105, 17, 'banner_id', '轮播图ID', 'varchar(255)', 'String', 'bannerId', '1', '0', '0', '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2025-11-23 14:26:37', '', '2025-11-23 14:26:57');
INSERT INTO `gen_table_column` VALUES (106, 17, 'dishes_id', '菜品ID', 'varchar(255)', 'String', 'dishesId', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 2, 'admin', '2025-11-23 14:26:37', '', '2025-11-23 14:26:57');
INSERT INTO `gen_table_column` VALUES (107, 17, 'sort', '排序', 'int', 'Long', 'sort', '0', '0', '1', '1', '1', '1', '0', 'EQ', 'input', '', 3, 'admin', '2025-11-23 14:26:37', '', '2025-11-23 14:26:57');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`  (
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '订单号',
  `total_price` decimal(10, 2) NOT NULL COMMENT '总价',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '待支付' COMMENT '状态',
  `user_id` int NOT NULL COMMENT '用户ID',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  UNIQUE INDEX `order_id`(`order_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '订单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('OR20260415170750103', 18.80, '已完成', 103, '2026-04-15 17:07:50');

-- ----------------------------
-- Table structure for order_od
-- ----------------------------
DROP TABLE IF EXISTS `order_od`;
CREATE TABLE `order_od`  (
  `od_id` int NOT NULL AUTO_INCREMENT COMMENT '订单菜品ID',
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '订单号',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜品名称',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜品图片',
  `quantity` int NOT NULL COMMENT '数量',
  `price` decimal(10, 2) NOT NULL COMMENT '单价',
  PRIMARY KEY (`od_id`) USING BTREE,
  UNIQUE INDEX `od_id`(`od_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '订单菜品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_od
-- ----------------------------
INSERT INTO `order_od` VALUES (23, 'OR20260415170750103', '红烧牛肉面', '/profile/upload/2026/04/15/红烧牛肉面_20260415165927A007.png', 1, 18.80);

-- ----------------------------
-- Table structure for recommend
-- ----------------------------
DROP TABLE IF EXISTS `recommend`;
CREATE TABLE `recommend`  (
  `recommend_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '营养推荐ID',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '标题',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '描述',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '图片',
  `calories` int NOT NULL COMMENT '大卡',
  PRIMARY KEY (`recommend_id`) USING BTREE,
  UNIQUE INDEX `recommend_id`(`recommend_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '营养推荐' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of recommend
-- ----------------------------
INSERT INTO `recommend` VALUES ('36996267eaf44cc4ba0efd8b89421aac', '碳水化合物套餐', '全麦面包+香蕉+酸奶，提供充足能量', '/profile/upload/2026/04/15/碳水化合物套餐_20260415170638A055.png', 380);
INSERT INTO `recommend` VALUES ('4377ffcec51e4144ab91404f8f3db0b4', '蛋白质丰富套餐', '鸡胸肉+西兰花+糙米饭，优质蛋白助力肌肉生长', '/profile/upload/2026/04/15/蛋白质丰富套餐_20260415170644A056.png', 450);
INSERT INTO `recommend` VALUES ('8f317fbc6e4a448199fea0be5636b76b', '维生素补充套餐 ', '彩虹沙拉+牛油果+坚果，丰富的维生素和纤维', '/profile/upload/2026/04/15/维生素补充套餐_20260415170650A057.png', 320);

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '系统默认是');
INSERT INTO `sys_dict_data` VALUES (9, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '系统默认否');
INSERT INTO `sys_dict_data` VALUES (99, 1, '待支付', '待支付', 'order_status', NULL, 'warning', 'N', '0', 'admin', '2025-11-21 10:27:43', 'admin', '2025-11-21 10:28:00', NULL);
INSERT INTO `sys_dict_data` VALUES (100, 2, '制作中', '制作中', 'order_status', NULL, 'primary', 'N', '0', 'admin', '2025-11-21 10:28:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (101, 3, '待取餐', '待取餐', 'order_status', NULL, 'info', 'N', '0', 'admin', '2025-11-21 10:28:32', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (102, 4, '已完成', '已完成', 'order_status', NULL, 'success', 'N', '0', 'admin', '2025-11-21 10:28:49', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (103, 5, '已取消', '已取消', 'order_status', NULL, 'danger', 'N', '0', 'admin', '2025-11-21 10:29:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (104, 1, '功能建议', '功能建议', 'feedback_type', NULL, 'primary', 'N', '0', 'admin', '2025-11-23 12:01:17', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (105, 2, '问题反馈', '问题反馈', 'feedback_type', NULL, 'warning', 'N', '0', 'admin', '2025-11-23 12:01:26', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (106, 3, '投诉意见', '投诉意见', 'feedback_type', NULL, 'danger', 'N', '0', 'admin', '2025-11-23 12:01:35', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (107, 4, '表扬鼓励', '表扬鼓励', 'feedback_type', NULL, 'success', 'N', '0', 'admin', '2025-11-23 12:01:44', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (108, 5, '其他', '其他', 'feedback_type', NULL, 'info', 'N', '0', 'admin', '2025-11-23 12:01:52', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 101 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '系统是否', 'sys_yes_no', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (99, '订单状态', 'order_status', '0', 'admin', '2025-11-21 10:27:11', '', NULL, NULL);
INSERT INTO `sys_dict_type` VALUES (100, '反馈类型', 'feedback_type', '0', 'admin', '2025-11-23 12:01:06', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由参数',
  `route_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '路由名称',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int NULL DEFAULT 1 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2014 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, 99, 'system', NULL, '', '', 1, 0, 'M', '0', '0', '', '系统管理', 'admin', '2025-08-30 08:59:54', 'admin', '2025-11-19 11:44:30', '系统管理目录');
INSERT INTO `sys_menu` VALUES (100, '用户管理', 1, 1, 'user', 'system/user/index', '', '', 1, 1, 'C', '0', '0', 'system:user:list', '用户管理', 'admin', '2025-08-30 08:59:54', 'admin', '2025-09-01 11:20:55', '用户管理菜单');
INSERT INTO `sys_menu` VALUES (101, '角色管理', 1, 2, 'role', 'system/role/index', '', '', 1, 1, 'C', '0', '0', 'system:role:list', '角色管理', 'admin', '2025-08-30 08:59:54', 'admin', '2025-09-01 11:26:22', '角色管理菜单');
INSERT INTO `sys_menu` VALUES (102, '菜单管理', 1, 3, 'menu', 'system/menu/index', '', '', 1, 1, 'C', '0', '0', 'system:menu:list', '菜单管理', 'admin', '2025-08-30 08:59:54', 'admin', '2025-09-01 11:26:28', '菜单管理菜单');
INSERT INTO `sys_menu` VALUES (105, '字典管理', 1, 6, 'dict', 'system/dict/index', '', '', 1, 1, 'C', '0', '0', 'system:dict:list', '字典管理', 'admin', '2025-08-30 08:59:54', 'admin', '2025-09-01 11:26:38', '字典管理菜单');
INSERT INTO `sys_menu` VALUES (2005, '菜品分类管理', 0, 1, 'category', 'canteen/category/index', NULL, '', 1, 1, 'C', '0', '0', 'canteen:category:list', '菜品分类管理', 'admin', '2025-11-19 11:40:07', 'admin', '2025-11-19 11:46:48', '菜品分类菜单');
INSERT INTO `sys_menu` VALUES (2006, '菜品管理', 0, 2, 'dishes', 'canteen/dishes/index', NULL, '', 1, 1, 'C', '0', '0', 'canteen:dishes:list', '菜品管理', 'admin', '2025-11-19 13:39:02', 'admin', '2025-11-19 13:42:24', '菜品菜单');
INSERT INTO `sys_menu` VALUES (2007, '收藏管理', 0, 3, 'collect', 'canteen/collect/index', NULL, '', 1, 1, 'C', '0', '0', 'canteen:collect:list', '收藏管理', 'admin', '2025-11-20 10:20:59', 'admin', '2025-11-20 10:26:26', '收藏菜单');
INSERT INTO `sys_menu` VALUES (2008, '购物车管理', 0, 4, 'cart', 'canteen/cart/index', NULL, '', 1, 1, 'C', '0', '0', 'canteen:cart:list', '购物车管理', 'admin', '2025-11-20 14:48:02', 'admin', '2025-11-20 14:50:57', '购物车菜单');
INSERT INTO `sys_menu` VALUES (2009, '订单管理', 0, 5, 'order', 'canteen/order/index', NULL, '', 1, 1, 'C', '0', '0', 'canteen:order:list', '订单管理', 'admin', '2025-11-21 10:31:45', 'admin', '2025-11-21 10:34:16', '订单菜单');
INSERT INTO `sys_menu` VALUES (2010, '营养推荐管理', 0, 6, 'recommend', 'canteen/recommend/index', NULL, '', 1, 1, 'C', '0', '0', 'canteen:recommend:list', '营养推荐管理', 'admin', '2025-11-23 10:31:56', 'admin', '2025-11-23 10:34:21', '营养推荐菜单');
INSERT INTO `sys_menu` VALUES (2011, '健康小贴士管理', 0, 7, 'tip', 'canteen/tip/index', NULL, '', 1, 1, 'C', '0', '0', 'canteen:tip:list', '健康小贴士管理', 'admin', '2025-11-23 11:10:00', 'admin', '2025-11-23 11:12:08', '健康小贴士菜单');
INSERT INTO `sys_menu` VALUES (2012, '用户意见反馈', 0, 8, 'feedback', 'canteen/feedback/index', NULL, '', 1, 1, 'C', '0', '0', 'canteen:feedback:list', '用户意见反馈', 'admin', '2025-11-23 12:03:18', 'admin', '2025-11-23 12:05:22', '意见反馈菜单');
INSERT INTO `sys_menu` VALUES (2013, '轮播图管理', 0, 9, 'banner', 'canteen/banner/index', NULL, '', 1, 1, 'C', '0', '0', 'canteen:banner:list', '轮播图管理', 'admin', '2025-11-23 14:27:32', 'admin', '2025-11-23 14:29:29', '轮播图菜单');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) NULL DEFAULT 1 COMMENT '部门树选择项是否关联显示',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 99 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, '1', 1, 1, '0', '0', 'admin', '2025-08-30 08:59:54', '', NULL, '超级管理员');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色和菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '密码',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '账号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `balance` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '饭卡余额',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 100, 'admin', '系统管理员', '00', 'huacai@163.com', '15888888888', '1', '', 'admin123', '0', '0', '127.0.0.1', '2026-04-15 16:58:11', 'admin', '2025-08-30 08:59:53', '', '2026-04-15 16:58:11', '管理员', 0.00);
INSERT INTO `sys_user` VALUES (103, NULL, '张三', '张三', '00', '', '', '0', '', '123456', '0', '0', '127.0.0.1', '2026-04-15 16:58:20', '', '2025-11-18 16:06:11', '', '2026-04-15 17:08:00', NULL, 97.90);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户和角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);

-- ----------------------------
-- Table structure for tip
-- ----------------------------
DROP TABLE IF EXISTS `tip`;
CREATE TABLE `tip`  (
  `tip_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '贴士ID',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '内容',
  `sort` int NOT NULL COMMENT '排序',
  PRIMARY KEY (`tip_id`) USING BTREE,
  UNIQUE INDEX `tip_id`(`tip_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '健康小贴士' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tip
-- ----------------------------
INSERT INTO `tip` VALUES ('13ee2e68bba14e718692c54b444bc32c', '三餐定时定量，避免暴饮暴食', 1);
INSERT INTO `tip` VALUES ('21c22e0df8914f18ae163eca1e84d68c', '多喝水，每天至少8杯水', 3);
INSERT INTO `tip` VALUES ('326e1e8fabaf4fe3992f27cf58899d9b', '减少加工食品，选择天然食材', 9);
INSERT INTO `tip` VALUES ('43ce9cc7d73640ddb2cc4a7c35bfb8c1', '控制油盐摄入，清淡饮食更健康', 4);
INSERT INTO `tip` VALUES ('469bc72e5ef14b44a158d5f2f8a550c1', '合理搭配荤素，保证营养均衡', 7);
INSERT INTO `tip` VALUES ('4f3b391a01f142459dbe833fb42c4e35', '细嚼慢咽，有助于消化吸收', 2);
INSERT INTO `tip` VALUES ('6dbd87509b864361a4474cf333a3e25a', '适量摄入坚果，补充健康脂肪', 8);
INSERT INTO `tip` VALUES ('7921d2a69225484f9dc05269d526543c', '少喝含糖饮料，预防肥胖', 5);
INSERT INTO `tip` VALUES ('e7c6f0fe549c4c15abeb89dd0c12245d', '多吃新鲜蔬果，补充维生素', 6);

SET FOREIGN_KEY_CHECKS = 1;
